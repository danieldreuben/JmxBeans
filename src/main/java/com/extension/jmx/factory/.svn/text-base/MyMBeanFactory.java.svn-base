package com.extension.jmx.factory;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBean;

import com.extension.jmx.beans.MyDynamicMBean;
import com.extension.jmx.beans.MyModelMBean;

public class MyMBeanFactory {

      private static MyDynamicMBean dynamicMBean;
      public static MBeanServer mbeanServer;
     
      public static MyDynamicMBean getDynamicBean() {
    	mbeanServer= ManagementFactory.getPlatformMBeanServer();
	   
        try {
	          dynamicMBean = new MyDynamicMBean("input/Dynamic.properties");	   
	          mbeanServer.registerMBean(dynamicMBean, new ObjectName(dynamicMBean.getClass().getPackage()
	                            .getName() + ":type=" + dynamicMBean.getClass().getSimpleName()));
	               
		} catch (Exception e) {
			  e.printStackTrace();
		}
	    return dynamicMBean;
      }
      
      public static MyDynamicMBean getDynamicBean2(String name) {
    	mbeanServer= ManagementFactory.getPlatformMBeanServer();
	   
        try {
        	   dynamicMBean = new MyDynamicMBean("input/Dynamic.properties");	   
	          mbeanServer.registerMBean(dynamicMBean, new ObjectName(dynamicMBean.getClass().getPackage()
	                            .getName() + ":type=" + dynamicMBean.getClass().getSimpleName()+",group=test,name="+name));
	               
		} catch (Exception e) {
			  e.printStackTrace();
		}
	    return dynamicMBean;
      }  
      
    /**
     * Register the provided ModelMBean in the MBeanServer using the provided
     * ObjectName String.
     *
     * @param modelMBean ModelMBean to be registered with the MBeanServer.
     * @param objectNameString ObjectName of the registered ModelMBean.
     */
     /*private static void registerModelMBean(final ModelMBean modelMBean,
                                    final String objectNameString) {

            try {
                final ObjectName objectName = new ObjectName(objectNameString);
                mbeanServer= ManagementFactory.getPlatformMBeanServer();             
                mbeanServer.registerMBean(modelMBean, objectName);
            } catch (MalformedObjectNameException badObjectName) {
                System.err.println("ERROR trying to generate ObjectName based on " +
                      objectNameString + ":\n" + badObjectName.getMessage());
            } catch (InstanceAlreadyExistsException objectNameAlreadyExists) {
                System.err.println("ERROR due to ObjectName [" + objectNameString +
                                   "] already existing: " +
                                   objectNameAlreadyExists.getMessage());
            } catch (MBeanRegistrationException badMBeanRegistration) {
                System.err.println("ERROR trying to register MBean with ObjectName " +
                                   objectNameString + ":\n" +
                    badMBeanRegistration.getMessage());
            } catch (NotCompliantMBeanException nonCompliantMBean) {
                System.err.println("ERROR trying to register nonconforming MBean " +
                                   "with ObjectName of " + objectNameString +
                                   nonCompliantMBean.getMessage());
            }
    }*/

  
    /**
     * Create a ModelMBean in the raw and register it with the MBeanServer.
     */
    /*public static ModelMBean getModelMBean() {
      
            MyModelMBean mbd = new MyModelMBean();
            ModelMBean bean = mbd.createRawModelMBean();
            String rawModelMBeanObjectNameString = "modelmbean:type=raw";
            registerModelMBean(bean, rawModelMBeanObjectNameString);
            return bean;
    }    */  
}