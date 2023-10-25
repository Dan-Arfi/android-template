import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.webkit.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.app1697879824973.R

class MainActivity : AppCompatActivity() {

    private lateinit var urlWebView: WebView
    private lateinit var noInternetLayout: LinearLayout
    private var url: String = "https://dev.to/tqbit/quick-dirty-how-to-deploy-a-fullstack-vue-js-app-with-a-working-node-js-backend-51k4" // Default URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        urlWebView = findViewById(R.id.urlWebView)
        noInternetLayout = findViewById(R.id.noInternetLayout)
        val webSettings: WebSettings = urlWebView.settings
        webSettings.javaScriptEnabled = true // Enable JavaScript
        webSettings.domStorageEnabled = true // Enable DOM Storage
        webSettings.allowFileAccess = true // Allow access to file
        webSettings.allowContentAccess = true // Allow content access

        urlWebView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                // Display custom no internet connection layout
                urlWebView.visibility = WebView.GONE
                noInternetLayout.visibility = LinearLayout.VISIBLE
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return when {
                    url.startsWith("tel:") -> {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse(url)
                        startActivity(intent)
                        true
                    }
                    url.startsWith("mailto:") -> {
                        val intent = Intent(Intent.ACTION_SENDTO)
                        intent.data = Uri.parse(url)
                        startActivity(intent)
                        true
                    }
                    url.startsWith("sms:") -> {
                        val intent = Intent(Intent.ACTION_SENDTO)
                        intent.data = Uri.parse(url)
                        startActivity(intent)
                        true
                    }
                    url.startsWith("http:") || url.startsWith("https:") -> {
                        view.loadUrl(url)
                        false
                    }
                    else -> {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(intent)
                            true
                        } catch (e: Exception) {
                            false
                        }
                    }
                }
            }
        }
        urlWebView.loadUrl(url)
    }

    override fun onBackPressed() {
        if (urlWebView.canGoBack()) {
            urlWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
