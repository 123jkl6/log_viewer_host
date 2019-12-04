package com.logviewer.logviewer.model.logviewer;

import java.time.LocalDate;

public class FileListRequest {
    private LocalDate date;
    private boolean server;
    private boolean middleware;
    private boolean sso;
    private boolean tms;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isServer() {
        return server;
    }

    public void setServer(boolean server) {
        this.server = server;
    }

    public boolean isSso() {
        return sso;
    }

    public void setSso(boolean sso) {
        this.sso = sso;
    }

    public boolean isTms() {
        return tms;
    }

    public void setTms(boolean tms) {
        this.tms = tms;
    }

    public boolean isMiddleware() {
        return middleware;
    }

    public void setMiddleware(boolean middleware) {
        this.middleware = middleware;
    }
}
