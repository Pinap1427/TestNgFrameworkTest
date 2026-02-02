package SampleTestingMayur;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class UnirestExample {
    private static final String URL = "https://api.dev.cxostory.in/v1/configuration/api/v1/db/data/v1/linkcxo/categories?limit=500&fields=Id,Categories";
    private static final String TOKEN = "Bearer MeTCxqIbUpPesE6G9hcl0ZYh0m8Ffpas2oRfLMVZ3CnEOSzTKhqADcmS4Oajo3POeS061BlFP0OjTvdr4vvIwwI8FYlL2y4Eo487UHw6EU7951sWds6Y0xbeCwFVaOME";

    public static void main(String[] args) {
        try {
            int retries = 3;
            for (int i = 0; i < retries; i++) {
                HttpResponse<String> response = Unirest.get(URL)
                        .header("Authorization", TOKEN) // ✅ correct header
                        .header("Accept", "*/*")
                        .asString();

                System.out.println("Requesting URL: " + URL);
                System.out.println("Response Status: " + response.getStatus());
                System.out.println("Response Body: " + response.getBody());

                if (response.getStatus() == 200) {
                    break; // ✅ success
                } else {
                    System.out.println("Retrying... (" + (i + 1) + "/" + retries + ")");
                    Thread.sleep(3000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
