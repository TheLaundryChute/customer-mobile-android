package com.inMotion.core.objects.meta;

/**
 * Created by sfbechtold on 12/9/15.
 */
public enum AccessControl  {
    None,
    Viewer,
    Editor,
    Owner,
    Everyone;

    
    public static AccessControl parse(Integer value) {
        switch (value) {
            case 0: return AccessControl.None;
            case 1: return AccessControl.Viewer;
            case 2: return AccessControl.Editor;
            case 3: return AccessControl.Owner;
            case 4: return AccessControl.Everyone;
            default: return AccessControl.None;
        }
    }
}


