package com.smeanox.games.ld34.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.LD34;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(Consts.DEV_WIDTH, Consts.DEV_HEIGHT);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new LD34();
        }
}