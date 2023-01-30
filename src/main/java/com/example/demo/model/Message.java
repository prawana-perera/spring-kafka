package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Message(@JsonProperty("user") String user, @JsonProperty("contents") String contents) {
}
