package suite.groovy.sources

import com.griddynamics.jagger.invoker.InvocationException
import com.griddynamics.jagger.invoker.Invoker

import java.io.BufferedReader;


/**
 * Created with IntelliJ IDEA.
 * User: nmusienko
 * Date: 11.03.13
 * Time: 11:45
 * To change this template use File | Settings | File Templates.
 */

class GroovyPageVisitorInvoker implements Invoker{

        public GroovyPageVisitorInvoker() {
        }
        private String getUrl(String endpoint, String path) {
            return endpoint + "/" + path;
        }

        private String doHttpGet(String urlStr) throws IOException {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setInstanceFollowRedirects(false);
            connection.setUseCaches(false);

            InputStream is = connection.getInputStream();

            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String theLine;

            StringBuilder response = new StringBuilder();
            while ((theLine = br.readLine()) != null) {
                response.append(theLine);
            }
            connection.disconnect();

            return response.toString();
        }

        @Override
        public String toString() {
            return "Groovy Invoker";
        }

    @Override
    Object invoke(Object query, Object endpoint) throws InvocationException {
        try {
            return doHttpGet(getUrl((String)endpoint, (String)endpoint));
        } catch (Exception e) {
            throw new InvocationException(e.getMessage(), e);
        }
    }
}
