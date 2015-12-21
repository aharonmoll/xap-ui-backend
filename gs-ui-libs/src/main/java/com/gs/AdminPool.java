package com.gs;

import org.openspaces.admin.Admin;
import org.openspaces.admin.internal.admin.DefaultAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: aharon
 * Date: 12/11/13
 * Time: 6:02 PM
 */
public class AdminPool<K,V> {

    Map<K,V> pool = new HashMap<K, V>();

//    public Admin getAdmin(){
//        Admin admin = new DefaultAdmin();
//    }
}
