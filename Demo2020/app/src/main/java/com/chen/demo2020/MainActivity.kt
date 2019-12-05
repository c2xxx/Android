package com.chen.demo2020

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.chen.demo2020.test.sign.Test
import kotlinx.android.synthetic.main.activity_main.*
import com.chen.utils.Logger;

//import com.chen.utils.RSAUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        btn_btn001.setOnClickListener(View.OnClickListener {
            doEncode();
        })
        btn_btn002.setOnClickListener(View.OnClickListener {
            doEncode();
        })
        btn_btn003.setOnClickListener(View.OnClickListener {
            doTest();
        })
    }

    private fun doTest() {
        Test.test()
    }

    private fun doEncode() {
        Logger.d("doEncode");


        var keyMap = RSAUtils.createKeys(1024)
        var publicKeyStr = keyMap.get("publicKey")
        var privateKeyStr = keyMap.get("privateKey")

        publicKeyStr =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCYv0iEp7nU7cNMVS0WClipNZhs1EfXsTlgyVmMkjRYTzjCBQYR2lX55+a1J7w7Ya+budO0dWS3btuVztONNaXYpifRu4eh72S4lYohDIKHbkCsKkmrgGfWYihqBKtwv/IXMT8BYEutf47Hil5M9FM8N3gL3C2Xr58cB1rbIJRhtwIDAQAB"
        privateKeyStr =
            "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJi/SISnudTtw0xVLRYKWKk1mGzUR9exOWDJWYySNFhPOMIFBhHaVfnn5rUnvDthr5u507R1ZLdu25XO0401pdimJ9G7h6HvZLiViiEMgoduQKwqSauAZ9ZiKGoEq3C/8hcxPwFgS61/jseKXkz0Uzw3eAvcLZevnxwHWtsglGG3AgMBAAECgYEAjWa+fjc627fDTJ2pIDcxvmrkERMu8qiYYlXjNTN/i6TC+TFnXg2N+8vHEjqXkS0sQO/04KCwPPM+Cd+ujZF1VbzUcbb0J5l79Cq0SCdJmmnZqAk50dZX/b/OeMZlBYDGCmP25iw7mK3yHIKXowIfofHFx5878zILocA/GDlysMECQQDFyWfoitZMptP1I3ecVZbR506mxIH6lPhCiWq9XdRu5TzEPw9ELDhS93Z2MjfxGGf4UxBbQwuQoPmgA7oYT1ehAkEAxbRK/4jXnM1sOKFaZv+4MUnkdjuyyUZnljziZ3yvYsuuN4Gn/eCspJs430Hv6izbjrtZ4BIFIrCYde2fum9aVwJBAIq3cxT5KsufxdCMC3ut25Vj8RmtgKvKh0PDNQWTJUaRqEjLIV3i1a36uS60kd+aCnFzcGlRYG8TN4AunPony4ECQQCYw8dvzH1OXCzTVVpVXCMncsFl2ps7i3tfS4xmCqI65MepfDYipgjA6G/5kyme+SpuD7ZSvZnaRDqKZSKXHJPhAkAXkKVlWlQ4smaihUoHZ9xJwxoS+QwPMJTXkSIApnwZE9/Z3Ehxgy09XEMBDh5o/4HF+bqzjayLtNnSYB2Kq4ZJ"
        Logger.d("publicKeyStr", publicKeyStr)
        Logger.d("privateKeyStr", privateKeyStr)

        var publicKey = RSAUtils.getPublicKey(publicKeyStr);
        var privateKey = RSAUtils.getPrivateKey(privateKeyStr);

        var content = "哈哈哈"
        var rsaContent = RSAUtils.publicEncrypt(content, publicKey)
        var content1 = RSAUtils.privateDecrypt(rsaContent, privateKey)

        Logger.d("content", content)
        Logger.d("rsaContent", rsaContent)
        Logger.d("content source=", content1)

        Logger.d("end doEncode");

    }
}
