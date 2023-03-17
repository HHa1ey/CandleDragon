package com.ha1ey.CandleDragon.plugin;

import java.util.List;

public interface VulPluginInfo {
    public final static int VUL_SEVERITY_LOW = 0;
    public final static int VUL_SEVERITY_MEDIUM = 1;
    public final static int VUL_SEVERITY_HIGH = 2;
    public final static String VUL_CATEGORY_RCE = "RCE";
    public final static String VUL_CATEGORY_SSTI = "SSTI";
    public final static String VUL_CATEGORY_JNDI_INJECT = "JNDI inject";
    public final static String VUL_CATEGORY_XXE = "XXE";
    public final static String VUL_CATEGORY_SSRF = "SSRF";
    public final String VUL_CATEGORY_SQL_INJECT = "SQL Inject";

    public void setVulPluginName(String name);

    public void setVulPluginVersion(String version);

    public void setVulPluginPath(String path);

    public void setVulPluginAuthor(String author);

    public void setVulName(String vulName);

    public void setVulId(String vid);

    public void setVulCVSS(double cvss);

    public void setVulSeverity(String severity);

    public void setVulCategory(String category);

    public void setVulProduct(String product);

    public void setVulScope(String scope);

    public void setVulDescription(String description);

    public void setVulDisclosureTime(String disclosureTime);

    public void registerPoc(Poc poc);

    public void registerExploit(List<Exploit> exploitList);

    public HelpPluginInfo getHelpPluginInfo();
}
