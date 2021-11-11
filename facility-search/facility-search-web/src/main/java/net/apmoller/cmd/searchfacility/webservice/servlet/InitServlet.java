package net.apmoller.cmd.searchfacility.webservice.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.apmoller.services.cmd.searchfacility.dao.SearchFacilityDAOLocal;

/**
 * Servlet implementation class InitServlet
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger			LOGGER	= Logger.getLogger(InitServlet.class.getName());
	@EJB(name = "SearchFacilityDAO")
	SearchFacilityDAOLocal searchFacilityDAOLocal;

	@Override
	public void init() {

		try {
			
			searchFacilityDAOLocal.hBeat();
		} catch (NamingException e) {
			LOGGER.info(e);
			LOGGER.error("Couldn't able to intailize log location",e);	  
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			searchFacilityDAOLocal.hBeat();
			response.getWriter().append("Served at: ").append(request.getContextPath());
		} catch (Exception e) {   
			LOGGER.info(e);
			LOGGER.error("Couldn't able to intailize log location",e);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	try{	
	doGet(request, response);
	}catch(Exception e)
	{
		LOGGER.info(e);
	}
	}

}
