/* Copyright 2017 Thomas Schneider
 *
 * This file is a part of Mastalab
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * Mastalab is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Mastalab; if not,
 * see <http://www.gnu.org/licenses>. */
package fr.gouv.etalab.mastodon.asynctasks;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import fr.gouv.etalab.mastodon.client.API;
import fr.gouv.etalab.mastodon.client.Entities.Results;
import fr.gouv.etalab.mastodon.interfaces.OnRetrieveSearchInterface;


/**
 * Created by Thomas on 25/05/2017.
 * Retrieves accounts and toots from search
 */

public class RetrieveSearchAsyncTask extends AsyncTask<Void, Void, Void> {

    private String query;
    private Results results;
    private OnRetrieveSearchInterface listener;
    private API api;
    private WeakReference<Context> contextReference;

    public RetrieveSearchAsyncTask(Context context, String query, OnRetrieveSearchInterface onRetrieveSearchInterface){
        this.contextReference = new WeakReference<>(context);
        this.query = query;
        this.listener = onRetrieveSearchInterface;
    }


    @Override
    protected Void doInBackground(Void... params) {
        api = new API(this.contextReference.get());
        results = api.search(query);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onRetrieveSearch(results, api.getError());
    }

}
