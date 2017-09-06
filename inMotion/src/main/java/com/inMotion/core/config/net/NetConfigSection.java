package com.inMotion.core.config.net;

import com.inMotion.core.config.ConfigSection;

import org.w3c.dom.Element;

import java.net.URL;

/**
 * Created by sfbechtold on 12/1/15.
 */
public class NetConfigSection extends ConfigSection {

    public NetConfigSection(Element secton) {
        super(secton);
    }

    private URL host = null;
    public URL getHost() {
        if (this.host == null){
            try {
                String value = this.getAsString("host");
                if (value != null)
                    this.host = new URL(value);
            }
            catch (Exception ex) {}
        }
        return this.host;
    }

    private URL web = null;
    public URL getWeb() {
        if (this.web == null) {
            try {
                String value = this.getAsString("web");
                if (value != null) {
                    this.web = new URL(value);
                }
            } catch (Exception ex) {}
        }
        return this.web;
    }

}
