package edu.sjsu.openstack.util;

import java.util.List;

import org.openstack4j.api.OSClient;
import org.openstack4j.api.exceptions.AuthenticationException;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.identity.User;
import org.openstack4j.model.network.Network;
import org.openstack4j.openstack.OSFactory;
/**
 * POC on Openstack4J
 * @author Anushri Srinath Aithal
 */
@Deprecated
public class OpenStackUtil {

	public void start() {
		OSClient os;
		try {
			
			OSFactory.enableHttpLoggingFilter(true);
			OSFactory.enableLegacyEndpointHandling(true);
			os = OSFactory.builderV3()
					.endpoint("http://localhost:5000/v3")
					.credentials("admin", "admin_user_secret", Identifier.byId("default"))
					.scopeToProject(Identifier.byName("admin"), Identifier.byId("default"))
					.authenticate();
			
			List<? extends Server> eastServers = os.compute().servers().list();
			
			List<? extends User> userList = os.identity().users().list();
			
			List<? extends Network> networks = os.networking().network().list();
			//System.out.println(networks.get(0).getSubnets().toString());
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
	}

}
