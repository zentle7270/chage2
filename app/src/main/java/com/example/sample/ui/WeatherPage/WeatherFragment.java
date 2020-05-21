package com.example.sample.ui.WeatherPage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sample.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class WeatherFragment extends Fragment{

    //private WeatherViewModel weatherViewModel;
    TextView text;
    //String data;
    //오늘의 년도날짜 받아오는 것
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
    Date date = new Date();
    String weather_date = formatter.format(date);
    //url 2시 고정
    String weather_url = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?serviceKey=95m7mGemWlThUNjzGE%2BDlk%2B3Oergfi0G0veBVR0kIqwjnmAkAct6%2FLzyt2nmysp%2BoPevwerShoS4CGBOMQs4uA%3D%3D"+"&pageNo=1&numOfRows=10&dataType=XML"+ "&base_date=" + weather_date + "&base_time=1400&nx=65&ny=123&";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        //this.onCreateView(savedInstanceState);
        /*weatherViewModel =
                ViewModelProviders.of(this).get(WeatherViewModel.class);

         */
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_weather, container, false);
        text = root.findViewById(R.id.text);
        /*
        weatherViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                new GetXMLTask().execute();
                //textView.setText(s);
            }
        });
         */
        new GetXMLTask().execute();
        return root;
    }

    public class GetXMLTask extends AsyncTask<String, Void, Document> {
        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            Document doc = null;
            try {
                url = new URL(weather_url);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();
            } catch (Exception e) {
                Toast.makeText(getContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        public void onPostExecute(Document doc) {
            String s = "";
            NodeList nodeList = doc.getElementsByTagName("item");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;
                NodeList idx = fstElmnt.getElementsByTagName("category");
                // 모든 category 값들을 출력 위한
                // s += "category = "+  idx.item(0).getChildNodes().item(0).getNodeValue() +"\n";
                // 강수확률 PDP, fcstValue 강수확률에 해당하는 값
                if (idx.item(0).getChildNodes().item(0).getNodeValue().equals("POP")) {
                    NodeList gugun = fstElmnt.getElementsByTagName("fcstValue");
                    s += "강수확률 : " + gugun.item(0).getChildNodes().item(0).getNodeValue() + "% \n";
                }
                // 습도 REH, fcstValue 습도에 해당하는 값
                if (idx.item(0).getChildNodes().item(0).getNodeValue().equals("REH")) {
                    NodeList gugun = fstElmnt.getElementsByTagName("fcstValue");
                    s += "습도 : " + gugun.item(0).getChildNodes().item(0).getNodeValue() + "% \n";
                }
                // 온도 T3H, fcstValue 온도에 해당하는 값
                if (idx.item(0).getChildNodes().item(0).getNodeValue().equals("T3H")) {
                    NodeList gugun = fstElmnt.getElementsByTagName("fcstValue");
                    s += "온도 : " + gugun.item(0).getChildNodes().item(0).getNodeValue() + "'C \n";
                }
                // 구름상태 SKY, fcstValue 구름상태에 해당하는 값
                // 0~2 : 맑음, 3~5 : 구름조금, 6~8 : 구름많음, 9~10 : 흐림
                if (idx.item(0).getChildNodes().item(0).getNodeValue().equals("SKY")) {
                    NodeList gugun = fstElmnt.getElementsByTagName("fcstValue");
                    int cloud_num = Integer.parseInt(gugun.item(0).getChildNodes().item(0).getNodeValue());
                    if (cloud_num == 0 || cloud_num == 1 || cloud_num == 2) {
                        s += "하늘상태 : 맑음\n";
                    } else if (cloud_num == 3 || cloud_num == 4 || cloud_num == 5) {
                        s += "하늘상태 : 구름 조금\n";
                    } else if (cloud_num == 6 || cloud_num == 7 || cloud_num == 8) {
                        s += "하늘상태 : 구름 많음\n";
                    } else if (cloud_num == 9 || cloud_num == 10) {
                        s += "하늘상태 : 흐림\n";
                        // s += "fcstValue 하늘상태 = "+  gugun.item(0).getChildNodes().item(0).getNodeValue() +"\n";
                    }
                    // 모든 카테고리에 대한 fcstValue 값들을 출력 위한
                    // NodeList gugun = fstElmnt.getElementsByTagName("fcstValue");
                    // s += "fcstValue = "+  gugun.item(0).getChildNodes().item(0).getNodeValue() +"\n";
                }
                text.setText(s);
                super.onPostExecute(doc);
            }
        }
    }
}
