package org.eu.miraikan.aiyou.model.worksai;

import org.eu.miraikan.aiyou.model.ModelAdapter;

public class WorkersAIAdapter<T> implements ModelAdapter<T> {
    protected String BASE_URL = "https://api.cloudflare.com";
    protected String API_TOKEN;
    protected String ACCOUNT_ID;
}
