package net.apmoller.maersk.cmd.geo.ejb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PoolUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.logging.log4j.LogManager;

import com.siperian.sif.client.EjbSiperianClient;
import com.siperian.sif.client.SiperianClient;

import net.apmoller.maersk.cmd.geo.bo.CityBO;
import net.apmoller.maersk.cmd.geo.bo.GEOBO;
import net.apmoller.maersk.cmd.geo.bo.ZipCodeBO;
import net.apmoller.maersk.cmd.geo.dao.CityDAO;
import net.apmoller.maersk.cmd.geo.dao.ZipCodeDAO;
import net.apmoller.maersk.cmd.geo.util.GEOConstants;


/**
 * Session Bean implementation class GeoServiceEJB
 */
@Stateless(name = "GeoServiceEJB")
public class GeoServiceEJB implements GeoServiceEJBRemote, GeoServiceEJBLocal {


	@Resource
	EJBContext ejbcontext;
	SiperianClient siperianClient;
	private Properties	properties	= null;

	private static final String	SUCCESS	= "SUCCESS";

	private ObjectPool<SiperianClient>	pool;
	//@Resource(name = GEOConstants.DATASOURCE_NAME)
	private DataSource	ds;

	public Properties			environmentProperties = new Properties();
	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(GeoServiceEJB.class);
	/**
	 * Default constructor.
	 */
	public GeoServiceEJB() {
		// TODO Auto-generated constructor stub

		//prop = PropertiesLoader.loadProperties();


		try{

			//logger.info(" Constructor GeoServiceEJB Starts ");
			System.out.println(" Constructor GeoServiceEJB Starts ");

			InitialContext context = new InitialContext();
			ds = (DataSource)context.lookup("jdbc/"+GEOConstants.DATASOURCE_NAME);

			//logger.info("########## InitialContext environment Properties :" + (String)context.lookup("CMD_ENVIRONMENT_CONFIG_LOCATION"));

			FileInputStream stream = new FileInputStream (new File(new URI((String)context.lookup("CMD_ENVIRONMENT_CONFIG_LOCATION"))));

			//logger.info("########## InitialContext environment Properties :" + stream);

			environmentProperties.load(stream);

			initLogger();
			System.out.println(" Constructor GeoServiceEJB Ends ");
			logger.info(" Constructor GeoServiceEJB Ends ");

		}catch(Exception exp){
			exp.printStackTrace(System.out);
			logger.error("Error in Constructor GeoServiceEJB ", exp);
		}

	}

	/**
	 * Inits the properties.
	 *
	 * @throws IOException
	 */
	@PostConstruct
	//@PostActivate
	private void initProperties() {
		try {
			initLogger(); // initialize external logging properties
		} catch (Exception e) {
			logger.fatal(e);

		}

	}

	public String loadEjb() {
		logger.info("### Geo Service EJB loaded ###");
		return SUCCESS;
	}

	protected void poolInit(BasePooledObjectFactory<SiperianClient> factory) {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMinIdle(Integer.valueOf(properties.getProperty("sipclient.minidle")));
		config.setMaxTotal(Integer.valueOf(properties.getProperty("sipclient.maxtotal")));
		config.setMaxIdle(Integer.valueOf(properties.getProperty("sipclient.maxidle")));
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		this.pool = PoolUtils.erodingPool(new GenericObjectPool<>(factory, config));
	}


	public SiperianClient getSIFClient() {
		try {
			logger.info("getSIFClient Starts");
			SiperianClient sifClient;
			sifClient = EjbSiperianClient.newSiperianClient(environmentProperties);
			logger.info("getSIFClient Ends");
			return sifClient;

		} catch (Exception e) {
			logger.error("Error getSIFClient : ", e);
			return null;
		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Map<String, String> upsertGEOData(GEOBO data) {
		// TODO Auto-generated method stub
		logger.info("upsertGEOData Starts");
		Connection connection = null;

		Map<String, String> responseMap = new LinkedHashMap<String, String>();

		try{

			if(GEOConstants.GEO_ENTITY_POSTAL_CODE.equals(data.getEntity())){
				logger.info((ZipCodeBO)data);

				connection = ds.getConnection();

				ZipCodeDAO zipdao = new ZipCodeDAO();

				zipdao.setConnection(connection);

				zipdao.setSiperianClient(getSIFClient());

				responseMap = zipdao.upsertGEOData((ZipCodeBO)data);

			}else if (GEOConstants.GEO_ENTITY_CITY.equals(data.getEntity())){
				logger.info((CityBO)data);

				connection = ds.getConnection();

				CityDAO citydao = new CityDAO();

				citydao.setConnection(connection);

				citydao.setSiperianClient(getSIFClient());

				responseMap = citydao.upsertGEOData((CityBO)data);

			}

			logger.info("upsertGEOData Ends");

			//this.pool.returnObject(siperianClient);

		}catch(Exception exp){
			exp.printStackTrace(System.out);
			logger.error("Error in upsertGEOData ", exp);
			ejbcontext.setRollbackOnly();

		}finally{
			try{
				if(connection != null){
					connection.close();
				}
				connection = null;

			}catch(Exception exp){
				logger.error("Error in upsertGEOData finially ", exp);
				exp.printStackTrace();
			}
		}

		//responseMap.put("STATUS", "SUCCESS");
		//responseMap.put("INTERACTIONID", "123456");

		return responseMap;
	}


	public Properties getEnvironmentProperties() {
		return this.environmentProperties;
	}

	private void initLogger() {
		org.apache.logging.log4j.core.LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		System.out.println("GeoServiceEJB Logger Location: "+ environmentProperties.getProperty("cmd.mugeo.external.log.config.file"));

		logger.info("GeoServiceEJB Logger Location: "+ environmentProperties.getProperty("cmd.mugeo.external.log.config.file"));

		File file = new File(environmentProperties.getProperty("cmd.mugeo.external.log.config.file"));
		logger.info("File location : "+ file);
		if (file.exists()) {
			// this will force a reconfiguration
			context.setConfigLocation(file.toURI());
			logger.info("#### External LOGGER configuration loaded ####");
			System.out.println("#### External LOGGER configuration loaded ####"+context.getLoggers());
		} else {
			logger.info("#### Internal LOGGER configuration loaded ####");
			System.out.println("#### Internal LOGGER configuration loaded ####");
		}

	}
 /*
	private String logRequest(GeoServiceRequest parameters) {
		// TODO Auto-generated method stub
		String message = null;
		logger.info("###Validation service request::");
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(GeoServiceRequest.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			StringWriter stringwriter = new StringWriter();
			jaxbMarshaller.marshal(new JAXBElement<GeoServiceRequest>(new QName("uri", "local"), GeoServiceRequest.class, parameters), stringwriter);
			message = stringwriter.toString();
			logger.info(message);
		} catch (PropertyException e) {
			// TODO Auto-generated catch block
			logger.fatal(e);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			logger.fatal(e);
		}
		return message;
	}
	*/
}
