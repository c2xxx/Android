package com.chen.demo2020.test.sign;

public class Test {
    //**  私钥密钥此处简单演示，应做成可配置    **/
    String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMwJf6SDuNirQKl6/pgC3WQDV10mP2Mwwivqzcb0BH4jE8wIeaOGQ5eBw8ncuz1xAFLA6RE0U7I/RxBehIGCKSMYFYfv1Mzd0y3UIXqkJUCyNgrJTu3vlEvydG3PVOeVn0jb6F55TJj1cO/Es14nwVhskjPk/Ft7ftrqoEBmgoidAgMBAAECgYAg1taIb/rsRIPfwz/+z1c6pZ8GCwXgvRRDZUNBZjzi4FprWGHbg9yVIfmVH8WzGeDncM0SS828vpp9c/j3ry9XgRDh70e2LKovEy9rXNenLyNjdQGCaH9WEcNaMrAwW/p2+a1DOZjtRc01yuLW/jNIlI4Sy8LmZ5bRqcp3bcjDsQJBAOgkUqBc+/rFoSxPs9+HoOU0NDpuKAQyUgTJEvlyYpzQ/ZixOy9gOygm0iCAddKgnzJMi4W9o0YlT5o3lX7aKUsCQQDhAbxxHWYv1aM98RJKOMHnvSO6Jvnn2HLdCL8qrMKdGADMexJsrJy9mXCkeYlEFWUQLAtZYQiHvbNL18trroy3AkBZC83SC7jweayYZb5WqRzzrrG2FBkveunxQfwQSWtAQf50+s78Hkqy3SlPJFeNwuUuEySV2aduudMuEdI7hY2/AkArE80DDvDYaZtWKYgp45HkDwb/BaVEqODcxmbrAaZEsyq7+zf8zFM5zV2Ob6JDAaGWpggKNZSPgFcKRycv13wjAkEAxO1AqU/hwfyZ8hSaROhqtnRrM05zQpDSPhSvHB1nv+qMw5pvJEK/YGDxm3zeEzef/vQhti8IFSo86cF9WMxWxw==";
    String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMCX+kg7jYq0Cpev6YAt1kA1ddJj9jMMIr6s3G9AR+IxPMCHmjhkOXgcPJ3Ls9cQBSwOkRNFOyP0cQXoSBgikjGBWH79TM3dMt1CF6pCVAsjYKyU7t75RL8nRtz1TnlZ9I2+heeUyY9XDvxLNeJ8FYbJIz5Pxbe37a6qBAZoKInQIDAQAB";

    public static void test() {
        System.out.println("测试start");
        Test test = new Test();
        test.doTest();
        System.out.println("测试end");
    }

    public void doTest() {
        String plain = "哈哈哈哈哈";
        try {
            String sign = VerifyUtil.sign(plain.getBytes(), privateKey);
//            boolean isOk = VerifyUtil.verify(sign.getBytes(), publicKey, sign);
            boolean isOk = VerifyUtil.verifySign(plain, publicKey, sign);

            System.out.println("sign==" + sign);
            System.out.println("isOk==" + isOk);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
