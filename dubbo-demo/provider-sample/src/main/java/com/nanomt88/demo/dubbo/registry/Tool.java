/**
 * Project: dubbo.registry.server-1.1.0-SNAPSHOT
 * <p>
 * File Created at 2010-7-27
 * <p>
 * Copyright 1999-2010 Alibaba.com Croporation Limited.
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Alibaba Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Alibaba.com.
 */
package com.nanomt88.demo.dubbo.registry;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.common.utils.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.*;

/**
 * Tool
 *
 * @author william.liangf
 */
public class Tool {

    private static final Comparator<String> SIMPLE_NAME_COMPARATOR = new Comparator<String>() {
        public int compare(String s1, String s2) {
            if (s1 == null && s2 == null) {
                return 0;
            }
            if (s1 == null) {
                return -1;
            }
            if (s2 == null) {
                return 1;
            }
            s1 = getSimpleName(s1);
            s2 = getSimpleName(s2);
            return s1.compareToIgnoreCase(s2);
        }
    };

    public static String toStackTraceString(Throwable t) {
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        t.printStackTrace(pw);
        return writer.toString();
    }

    public static boolean isContains(String[] values, String value) {
        return StringUtils.isContains(values, value);
    }

    public static boolean startWith(String value, String prefix) {
        return value.startsWith(prefix);
    }

    public static String getHostPrefix(String address) {
        if (address != null && address.length() > 0) {
            String hostname = getHostName(address);
            if (!address.startsWith(hostname)) {
                return "(" + hostname + ")";
            }
        }
        return "";
    }

    public static String getHostName(String address) {
        return NetUtils.getHostName(address);
    }

    public static String getHostAddress(String address) {
        if (address != null && address.length() > 0) {
            int i = address.indexOf(':');
            String port = address.substring(i + 1);
            String hostname = NetUtils.getHostName(address);
            if (!address.equals(hostname)) {
                return hostname + ":" + port;
            }
        }
        return "";
    }

    public static String getPath(String url) {
        try {
            return URL.valueOf(url).getPath();
        } catch (Throwable t) {
            return url;
        }
    }

    public static String getAddress(String url) {
        try {
            return URL.valueOf(url).getAddress();
        } catch (Throwable t) {
            return url;
        }
    }

    public static String getInterface(String service) {
        if (service != null && service.length() > 0) {
            int i = service.indexOf('/');
            if (i >= 0) {
                service = service.substring(i + 1);
            }
            i = service.lastIndexOf(':');
            if (i >= 0) {
                service = service.substring(0, i);
            }
        }
        return service;
    }

    public static String getGroup(String service) {
        if (service != null && service.length() > 0) {
            int i = service.indexOf('/');
            if (i >= 0) {
                return service.substring(0, i);
            }
        }
        return null;
    }

    public static String getVersion(String service) {
        if (service != null && service.length() > 0) {
            int i = service.lastIndexOf(':');
            if (i >= 0) {
                return service.substring(i + 1);
            }
        }
        return null;
    }

    public static String getIP(String address) {
        if (address != null && address.length() > 0) {
            int i = address.indexOf("://");
            if (i >= 0) {
                address = address.substring(i + 3);
            }
            i = address.indexOf('/');
            if (i >= 0) {
                address = address.substring(0, i);
            }
            i = address.indexOf('@');
            if (i >= 0) {
                address = address.substring(i + 1);
            }
            i = address.indexOf(':');
            if (i >= 0) {
                address = address.substring(0, i);
            }
            if (address.matches("[a-zA-Z]+")) {
                try {
                    address = InetAddress.getByName(address).getHostAddress();
                } catch (UnknownHostException e) {
                }
            }
        }
        return address;
    }

    public static String encode(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return url;
        }
    }


    public static int countMapValues(Map<?, ?> map) {
        int total = 0;
        if (map != null && map.size() > 0) {
            for (Object value : map.values()) {
                if (value != null) {
                    if (value instanceof Number) {
                        total += ((Number) value).intValue();
                    } else if (value.getClass().isArray()) {
                        total += Array.getLength(value);
                    } else if (value instanceof Collection) {
                        total += ((Collection<?>) value).size();
                    } else if (value instanceof Map) {
                        total += ((Map<?, ?>) value).size();
                    } else {
                        total += 1;
                    }
                }
            }
        }
        return total;
    }

    public static List<String> sortSimpleName(List<String> list) {
        if (list != null && list.size() > 0) {
            Collections.sort(list, SIMPLE_NAME_COMPARATOR);
        }
        return list;
    }

    public static String getSimpleName(String name) {
        if (name != null && name.length() > 0) {
            final int ip = name.indexOf('/');
            String v = ip != -1 ? name.substring(0, ip + 1) : "";

            int i = name.lastIndexOf(':');
            int j = (i >= 0 ? name.lastIndexOf('.', i) : name.lastIndexOf('.'));
            if (j >= 0) {
                name = name.substring(j + 1);
            }
            name = v + name;
        }
        return name;
    }

    public static String getParameter(String parameters, String key) {
        String value = "";
        if (parameters != null && parameters.length() > 0) {
            String[] pairs = parameters.split("&");
            for (String pair : pairs) {
                String[] kv = pair.split("=");
                if (key.equals(kv[0])) {
                    value = kv[1];
                    break;
                }
            }
        }
        return value;
    }

    public static Map<String, String> toParameterMap(String parameters) {
        return StringUtils.parseQueryString(parameters);
    }

    /**
     * 从provider的paramters参数中获取版本值
     *
     * @param parameters
     * @return
     */
    public static String getVersionFromPara(String parameters) {
        String version = "";
        if (parameters != null && parameters.length() > 0) {
            String[] params = parameters.split("&");
            for (String o : params) {
                String[] kv = o.split("=");
                if ("version".equals(kv[0])) {
                    version = kv[1];
                    break;
                }
            }
        }
        return version;
    }

//    public static boolean isProviderEnabled(Provider provider, List<Override> oList) {
//        for (Override o : oList) {
//            if (o.isMatch(provider)) {
//                Map<String, String> params = StringUtils.parseQueryString(o.getParams());
//                String disbaled = params.get(Constants.DISABLED_KEY);
//                if (disbaled != null && disbaled.length() > 0) {
//                    return !"true".equals(disbaled);
//                }
//            }
//        }
//        return provider.isEnabled();
//    }
//
//    public static int getProviderWeight(Provider provider, List<Override> oList) {
//        for (Override o : oList) {
//            if (o.isMatch(provider)) {
//                Map<String, String> params = StringUtils.parseQueryString(o.getParams());
//                String weight = params.get(Constants.WEIGHT_KEY);
//                if (weight != null && weight.length() > 0) {
//                    return Integer.parseInt(weight);
//                }
//            }
//        }
//        return provider.getWeight();
//    }
//
//    public void setOverrideService(OverrideService overrideService) {
//        this.overrideService = overrideService;
//    }
//
//    public void setRouteService(RouteService routeService) {
//        this.routeService = routeService;
//    }
//
//    public String formatTimestamp(String timestamp) {
//        if (timestamp == null || timestamp.length() == 0) {
//            return "";
//        }
//        return formatDate(new Date(Long.valueOf(timestamp)));
//    }
//
//    //时间格式化
//    public String formatDate(Date date) {
//        if (date == null) {
//            return "";
//        }
//        return DateFormatUtil.getDateFormat().format(date);
//    }
//
//    public String formatDate(Date date, String template) {
//        if (date == null || template == null) {
//            return "";
//        }
//        return DateFormatUtil.getDateFormat(template).format(date);
//    }

    public boolean beforeNow(Date date) {
        Date now = new Date();
        if (now.after(date)) {
            return true;
        }
        return false;
    }

//    //时间相减
//    public long dateMinus(Date date1, Date date2) {
//        return (date1.getTime() - date1.getTime()) / 1000;
//    }
//
//    public boolean isProviderEnabled(Provider provider) {
//        List<Override> oList = overrideService.findByServiceAndAddress(provider.getService(), provider.getAddress());
//        return isProviderEnabled(provider, oList);
//    }
//
//    public int getProviderWeight(Provider provider) {
//        List<Override> oList = overrideService.findByServiceAndAddress(provider.getService(), provider.getAddress());
//        return getProviderWeight(provider, oList);
//    }
//
//    public boolean isInBlackList(Consumer consumer) {
//        String service = consumer.getService();
//        List<Route> routes = routeService.findForceRouteByService(service);
//        if (routes == null || routes.size() == 0) {
//            return false;
//        }
//        String ip = getIP(consumer.getAddress());
//        for (Route route : routes) {
//            try {
//                if (!route.isEnabled()) {
//                    continue;
//                }
//                String filterRule = route.getFilterRule();
//                if (filterRule == null || filterRule.length() == 0 || "false".equals(filterRule)) {
//                    Map<String, MatchPair> rule = RouteRule.parseRule(route.getMatchRule());
//                    MatchPair pair = rule.get("consumer.host");
//                    if (pair == null) {
//                        pair = rule.get("host");
//                    }
//                    if (pair != null) {
//                        if (pair.getMatches() != null && pair.getMatches().size() > 0) {
//                            for (String host : pair.getMatches()) {
//                                if (ParseUtils.isMatchGlobPattern(host, ip)) {
//                                    return true;
//                                }
//                            }
//                        }
//                        if (pair.getUnmatches() != null && pair.getUnmatches().size() > 0) {
//                            boolean forbid = true;
//                            for (String host : pair.getUnmatches()) {
//                                if (ParseUtils.isMatchGlobPattern(host, ip)) {
//                                    forbid = false;
//                                }
//                            }
//                            if (forbid) {
//                                return true;
//                            }
//                        }
//                    }
//                }
//            } catch (ParseException e) {
//                throw new RuntimeException(e.getMessage(), e);
//            }
//        }
//        return false;
//    }
//
//    public String getConsumerMock(Consumer consumer) {
//        return getOverridesMock(consumer.getOverrides());
//    }

//    public String getOverridesMock(List<Override> overrides) {
//        if (overrides != null && overrides.size() > 0) {
//            for (Override override : overrides) {
//                Map<String, String> params = StringUtils.parseQueryString(override.getParams());
//                String mock = params.get("mock");
//                if (mock != null && mock.length() > 0) {
//                    return mock;
//                }
//            }
//        }
//        return "";
//    }
//
//    public boolean checkUrl(User user, String uri) {
//        return true;
//        /*if(!User.ROOT.equals(user.getRole())){
//            List<String> disabledSysinfo = new ArrayList<String>();
//            List<String> disabledSysmanage = new ArrayList<String>();
//            Map<String, Boolean> features = daoCache.getFeatures();
//            if (features.size() > 0){
//                for(Entry<String,Boolean> feature : features.entrySet()){
//                    if(feature.getKey().startsWith("Sysinfo") && !feature.getValue()){
//                        disabledSysinfo.add(feature.getKey().replace(".", "/").toLowerCase());
//                    }else if(feature.getKey().startsWith("Sysmanage") && !feature.getValue()){
//                        disabledSysmanage.add(feature.getKey().replace(".", "/").toLowerCase());
//                    }
//                }
//                if(uri.startsWith("/sysinfo")){
//                    for(String disabled : disabledSysinfo){
//                        if (uri.contains(disabled)){
//                            return false;
//                        }
//                    }
//                }
//                if(uri.startsWith("/sysmanage")){
//                    for(String disabled : disabledSysmanage){
//                        if (uri.contains(disabled)){
//                            return false;
//                        }
//                    }
//                }
//            }else{
//                return true;
//            }
//        }
//        return true;*/
//    }
}
