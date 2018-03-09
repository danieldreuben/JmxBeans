package com.extension.jmx.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Iterator;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.ReflectionException;

import com.extension.jmx.api.IMyDynamicMBean;


public class MyDynamicMBean implements DynamicMBean, IMyDynamicMBean {
      private final String propertyFileName;
      private final Properties properties;

      public MyDynamicMBean(String propertyFileName) throws IOException {

            this.propertyFileName = propertyFileName;
            this.properties = new Properties();
            //load();
      }

      public synchronized String getAttribute(String name)
                  throws AttributeNotFoundException {

            String value = this.properties.getProperty(name);

            if (value != null) {
                  return value;
            }
            throw new AttributeNotFoundException("No such property: " + name);
      }


      public synchronized void setAttribute(Attribute attribute)
                  throws InvalidAttributeValueException, MBeanException,
                        AttributeNotFoundException {

            String name = attribute.getName();

            if (this.properties.getProperty(name) == null)
                  throw new AttributeNotFoundException(name);

            Object value = attribute.getValue();

            if (!(value instanceof String)) {
                  throw new InvalidAttributeValueException(
                              "Attribute value not a string: " + value);

            }

            this.properties.setProperty(name, (String) value);
            System.out.println(name + " " + value);
/*
            try {
                  save();

            } catch (IOException e) {
                  throw new MBeanException(e);
            }*/
      }


      public void setAttribute(String name, String value) {

            Attribute attribute = new Attribute(name, value);
            AttributeList list = new AttributeList();

            list.add(attribute);
            setAttributes(list);
      }


      public synchronized AttributeList getAttributes(String[] names) {

            AttributeList list = new AttributeList();

            for (String name : names) {
                  String value = this.properties.getProperty(name);

                  if (value != null)
                        list.add(new Attribute(name, value));
            }
            return list;
      }


      public synchronized AttributeList setAttributes(AttributeList list) {

            Attribute[] attrs = (Attribute[]) list.toArray(new Attribute[0]);
            AttributeList retlist = new AttributeList();

            for (Attribute attr : attrs) {
                  String name = attr.getName();
                  Object value = attr.getValue();

                  if ((value instanceof String)) {
                        this.properties.setProperty(name, (String) value);
                        retlist.add(new Attribute(name, value));
                  }
            }

            try {
                  save();

            } catch (IOException e) {
                  return new AttributeList();
            }

            return retlist;

      }


      public Object invoke(String name, Object[] args, String[] sig)
                  throws MBeanException, ReflectionException {

            if ((name.equals("reload")) && ((args == null) || (args.length == 0))
                        && ((sig == null) || (sig.length == 0))) {
            	return null;
                  /*try {
                        load();
                        return null;

                  } catch (IOException e) {
                        throw new MBeanException(e);
                  }*/
            }
            if (name.equals("getAttribute") && args.length == 1) {
            	try {
					return getAttribute(args[0].toString());
				} catch (AttributeNotFoundException e) {
					e.printStackTrace();
				}
            }
            throw new ReflectionException
            	(new NoSuchMethodException(name));
      }


      public synchronized MBeanInfo getMBeanInfo() {

            SortedSet names = new TreeSet();

            for (Iterator localIterator1 = this.properties.keySet().iterator(); localIterator1
                        .hasNext();) {
                  Object name = localIterator1.next();
                  names.add((String) name);
            }

            MBeanAttributeInfo[] attrs = new MBeanAttributeInfo[names.size()];
            Iterator it = names.iterator();

            for (int i = 0; i < attrs.length; i++) {
                  String name = (String) it.next();
                  attrs[i] = new MBeanAttributeInfo(name, "java.lang.String",
                              "Property " + name, true, true, false);
            }

            MBeanOperationInfo[] opers = { new MBeanOperationInfo("reload",
                        "Reload properties from file", null, "void", 1) };

            return new MBeanInfo(getClass().getName(), "Property Manager MBean",
                        attrs, null, opers, null);
      }


      public void load()
      	throws IOException {

            InputStream inputStream = new FileInputStream(this.propertyFileName);
            this.properties.load(inputStream);
            inputStream.close();
      }

      private void save() throws IOException {

            String newPropertyFileName = this.propertyFileName;
            File file = new File(newPropertyFileName);
            OutputStream output = new FileOutputStream(file);
            String comment = "Written by " + getClass().getName();

            this.properties.store(output, comment);
            output.close();

            if (!file.renameTo(new File(this.propertyFileName)))
                  throw new IOException("Rename " + newPropertyFileName + " to "
                              + this.propertyFileName + " failed");
      }
}