package net.apmoller.fct.geowriteback.webservice.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.apmoller.maersk.services.fct.geowrite.messaging.GeoPublishManagerLocal;


/**
 * Servlet implementation class InitServlet
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(InitServlet.class.getName());

	@EJB(name = "GeoPublishManager")
	private GeoPublishManagerLocal customerPublishManager;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	@Override
	public void init() {
		try {
			customerPublishManager.hBeat();
		} catch (NamingException e) {
			LOGGER.fatal(e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			customerPublishManager.hBeat();
		} catch (NamingException e) {
			LOGGER.fatal(e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
