package SampleTestingMayur;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class UnirestExample {
    private static final String URL = "http://noco.dev.linkcxo.com/api/v1/db/data/noco/pamy4qwauxlp395/Cities-List/views/CitiesListExported17Csv?offset=0&limit=25&where=";
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImthaWxhc0BsaW5rY3hvLmNvbSIsImlkIjoidXMzZ2dsejc0MGV6bnV4ayIsInJvbGVzIjp7Im9yZy1sZXZlbC12aWV3ZXIiOnRydWV9LCJ0b2tlbl92ZXJzaW9uIjoiMGRmZDg1NTllZjg4YjcyZGRhNDg0YmIzNWU0NGY1Y2FmMmZmN2YzN2EyOTg0NmQ4Nzc2NDFmNTkzZGI5ODY3MmIxOGQ1M2I1ZjM5ZjY0MmUiLCJpYXQiOjE3NDIyNzY0OTYsImV4cCI6MTc0MjMxMjQ5Nn0.FemzYzd1F7kVuhfzdMp3OcYCeyXmAOHKuFm_e4lk4YU";

    public static void main(String[] args) {
        try {
            int retries = 3;
            for (int i = 0; i < retries; i++) {
                HttpResponse<String> response = Unirest.get(URL)
                        .header("xc-auth", TOKEN)
                        .asString();

                System.out.println("Requesting URL: " + URL);
                System.out.println("Response Status: " + response.getStatus());
                System.out.println("Response Body: " + response.getBody());

                if (response.getStatus() == 200) {
                    break; // Exit loop on success
                } else {
                    System.out.println("Retrying... (" + (i + 1) + "/" + retries + ")");
                    Thread.sleep(3000); // Wait for 3 seconds before retrying
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}