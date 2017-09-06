package com.inMotion.core.config;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.provider.DocumentsContract;

import com.inMotion.R;
import com.inMotion.core.config.net.NetConfigSection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by sfbechtold on 11/30/15.
 */
public class AppConfig extends ConfigSection {

    private Context context = null;
    private NetConfigSection network = null;
    private String clientId = null;
    private String organization = null;
    private Boolean isCustomer = null;

    public AppConfig(Element section, Context context) {
        super(section);
    }

    public String getClientId() {
        if (this.clientId == null)
            this.clientId = this.getAsString("clientId");
        return clientId;
    }

    public String getOrganization() {
        if (this.organization == null)
            this.organization = this.getAsString("organization");
        return organization;
    }

    public Boolean getIsCustomer() {
        if (this.isCustomer == null)
            this.isCustomer = this.getAsBoolean("isCustomer");
        return isCustomer;
    }

    public NetConfigSection getNetwork() {
        if (this.network == null) {
            this.network = this.getAsSection("net", NetConfigSection.class);
        }
        return network;
    }

    public Context getContext() {
        return this.context;
    }

    /** Singleton Init Properties **/

    public static void init(Context context) {
        if (current == null) {
            padlock.lock();
            if (current == null){

                try {
                    InputStream data = context.getResources().openRawResource(R.raw.inmotion_config);
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(data);
                    doc.getDocumentElement().normalize();

                    current = new AppConfig(doc.getDocumentElement(), context);
                }
                catch (Exception ex) {

                }
                padlock.unlock();
            }
        }
    }


    private static AppConfig current = null;
    private static Lock padlock = new ReentrantLock();
    public static AppConfig getCurrent() {
        return current;
    }

}
