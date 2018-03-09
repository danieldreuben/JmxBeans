package com.test;

import java.lang.management.ManagementFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Assert.*;

import com.extension.jmx.api.ICalculator;
import com.extension.jmx.api.IMyDynamicMBean;
import com.extension.jmx.beans.MyDynamicMBean;
import com.extension.jmx.beans.SimpleCalculator;
import com.extension.jmx.factory.MakeModelMBean;
import com.extension.jmx.factory.MyMBeanFactory;

import javax.management.JMX;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;


/**
 * Unit test for simple App.
 */
public class AppTest
    extends TestCase
{
    /**
     * Logger
     */
	//private static final Log log = LogFactory.getLog(AppTest.class);

	   /**
	    * testModelMBean
	    */
	   /*public void testJmxModelMBean() {
			try {
				Object result = null;

				RequiredModelMBean mb = (RequiredModelMBean) MyMBeanFactory.getModelMBean();
				String  opSig[] = { int.class.getName(), int.class.getName() };
				        Object  opParams[] = { 1, 2 };

				for (int i = 0; i < 10; i++)
					result = mb.invoke("add", opParams, opSig);

				System.out.println("Model MBean add op 1 + 2 = " + result);
				//assertEquals(result, null);
				assertFalse(false);
				//Thread.sleep(60000);
				return;
			} catch (MBeanException mbe) {
				mbe.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			assertFalse(true);
	   }*/

   /**
    * testDynamicMBean
    */
    public void testJmxDynamicMBean() {
        try {
            MyDynamicMBean mb = null;
            mb = MyMBeanFactory.getDynamicBean2("module1_1");
            mb.setAttribute("log", "in main class");
            mb.setAttribute("testing-123", "testingClass");
            Object result = mb.invoke("reload", null, null);
   		 	System.out.println("Dynamic MBean, simple reload = "
   		 			+ result);
   		 	
   		 	MyDynamicMBean mb2 =  MyMBeanFactory.getDynamicBean2("module1_2");
            mb2.setAttribute("service", "com.myservice");
            mb2.setAttribute("port", "1022");
		    
            System.out.println("Connect now with JConsole.");
		    System.out.println("Strike <Return> to exit.");
		    System.in.read();
		    assertTrue(true);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertFalse(true);
   }
    
    /**
     * testDynamicMBean
     */
     public void testMakeModelMBean() {
   	  try {

   		  SimpleCalculator cal = new SimpleCalculator();
   		 
   	      final ModelMBean mbean = MakeModelMBean.makeModelMBean(cal);
   	      ManagementFactory.getPlatformMBeanServer().
   	              registerMBean(mbean,new ObjectName(MakeModelMBean.getNaming(SimpleCalculator.class)));

   		  //Thread.sleep(60000);
   		  } catch (Exception e) {
   			  e.printStackTrace();
   		  }
    }

     /**
      * testDynamicMBeanProxy
      */
      /*public void testDynamicMBeanProxy() {
    	  try {

    		  ObjectName mbeanName = new ObjectName(MyDynamicMBean.class.getPackage()
                      .getName() + ":type=" + MyDynamicMBean.class.getSimpleName());
    	      MBeanServer mbeanServer;
    	      mbeanServer= ManagementFactory.getPlatformMBeanServer();
    	      IMyDynamicMBean mbeanProxy = JMX.newMBeanProxy(mbeanServer, mbeanName, 
    	    		  IMyDynamicMBean.class, true);

    	      System.out.println("\nCacheSize = " + mbeanProxy.getAttribute("log"));
     	      
       	      System.out.println("Connect now with JConsole.");
       	      System.out.println("Strike <Return> to exit.");
       	      System.in.read();
    	      assertTrue(true);
    	      return;
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  assertTrue(false);
     }  */   
      
      /**
       * testDynamicMBeanProxy
       */
       public void testModelMBeanProxy() {
     	  try {

     		  ObjectName mbeanName = new ObjectName(MakeModelMBean.getNaming(SimpleCalculator.class));
     	      MBeanServer mbeanServer;
     	      mbeanServer= ManagementFactory.getPlatformMBeanServer();
     	      ICalculator mbeanProxy = JMX.newMBeanProxy(mbeanServer, mbeanName, 
     	    		  ICalculator.class, true);

     	      System.out.println("Calculator:proxy= " + mbeanProxy.add(4, 44));
     	      assertTrue(true);

       	      
     	      return;
 		  } catch (Exception e) {
 			  e.printStackTrace();
 		  }
 		  assertTrue(false);
      }      
}
