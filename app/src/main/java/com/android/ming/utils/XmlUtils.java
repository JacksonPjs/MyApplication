package com.android.ming.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by YO on 2016/8/6.
 */
public class XmlUtils {


        public  static  String parseXml(Map<String,String> map){
                StringBuffer sb=new StringBuffer();
                sb.append("<xml>\n");
                Set es=map.entrySet();
                Iterator it=es.iterator();
                while (it.hasNext()){
                        Map.Entry entry= (Map.Entry) it.next();
                        String k= (String) entry.getKey();
                        String v= (String) entry.getValue();
                        if (null!=v&&!"".equals(v)&&!"appkey".equals(k)){
                                sb.append("<"+k+">"+map.get(k)+"</"+k+">\n");
                        }

                }
                sb.append("\n</xml>");
                return sb.toString();
        }
        public static String transMapToString(Map map){
                java.util.Map.Entry entry;
                StringBuffer sb = new StringBuffer();
                for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
                {
                        entry = (java.util.Map.Entry)iterator.next();
                        sb.append(entry.getKey().toString()).append( "'" ).append(null==entry.getValue()?"":
                                entry.getValue().toString()).append (iterator.hasNext() ? "^" : "");
                }
                return sb.toString();
        }
        public static Map transStringToMap(String mapString){
                Map map = new HashMap();
                java.util.StringTokenizer items;
                for(StringTokenizer entrys = new StringTokenizer(mapString, "^");entrys.hasMoreTokens();
                    map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
                        items = new StringTokenizer(entrys.nextToken(), "'");
                return map;
        }

//        public static Tresponse XMLStringToBean(String xml){
////                String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><student><age>22</age><classroom><grade>4</grade><id>1</id><name>软件工程</name></classroom><id>101</id><name>张三</name></student>";
//                try {
//
////                        final File file = new File("D:\\fileTest.xml");
//                        JAXBContext context = JAXBContext.newInstance(Tresponse.class);
//                        Unmarshaller unmarshaller = context.createUnmarshaller();
//                        Tresponse student = (Tresponse)unmarshaller.unmarshal(new StringReader(xml));
//                        System.out.println(student.toString());
//                        System.out.println(student.getOrderInfo().getPayUrl().toString());
//
////                        System.out.println(student.getOrderInfo().toString());
////                        System.out.println(student.getOrderInfo().getPayUrl());
//                        return student;
//                } catch (JAXBException e) {
//                        e.printStackTrace();
//                        return null;
//                }
//        }
}
