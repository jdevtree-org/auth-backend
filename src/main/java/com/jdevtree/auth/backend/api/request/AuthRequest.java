package com.jdevtree.auth.backend.api.request;


public record AuthRequest(String code, String redirectUrl) {}