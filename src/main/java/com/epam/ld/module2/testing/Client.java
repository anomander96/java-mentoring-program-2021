package com.epam.ld.module2.testing;

import java.util.Map;

/**
 * The type Client.
 */
public class Client {
    private String addresses;
    private Map<String, String> variables;

    /**
     * Gets addresses.
     *
     * @return the addresses
     */
    public String getAddresses() {
        return addresses;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public Map<String, String> getVariables() {
        return variables;
    }

    /**
     * Sets addresses.
     *
     * @param addresses the addresses
     */
    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    /**
     * Sets data.
     *
     * @param variables the data
     */
    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }
}
