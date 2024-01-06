package com.example.calculate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView calctext1;
    private TextView calctext2;
    private TextView calctext3;
    private TextView sisokutext;

    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnplus;
    private Button btnmainasu;
    private Button btnkakeru;
    private Button btnwaru;
    private Button btnikoru;
    private Button btnten;
    private Button btnplusmainasu;
    private Button btnclear;

    //文字列を加えて保存可能
    private StringBuilder sb;

    //合計数値　sbから代入される　小数点の計算が可能
    private double calcsum = 0;

    //状態移行管理 0:初期状態　１:calc1入力状態　２:四則演算入力状態　３:calc2入力状態　４:計算結果calc3表示状態
    private int calcphase = 0;

    //選択された四則記号　五種 "" + - ×　÷
    private String sisokuphase = "";
    //表示最大桁数
    private int numdigit = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calctext1 = findViewById(R.id.calctext1);
        calctext2 = findViewById(R.id.calctext2);
        calctext3 = findViewById(R.id.calctext3);
        sisokutext = findViewById(R.id.sisokutext);

        btn0 = findViewById(R.id.btn0);
        btn0.setOnClickListener(this);
        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        btn6 = findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        btn7 = findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
        btn8 = findViewById(R.id.btn8);
        btn8.setOnClickListener(this);
        btn9 = findViewById(R.id.btn9);
        btn9.setOnClickListener(this);
        btnplus = findViewById(R.id.btnplus);
        btnplus.setOnClickListener(this);
        btnmainasu = findViewById(R.id.btnmainasu);
        btnmainasu.setOnClickListener(this);
        btnkakeru = findViewById(R.id.btnkakeru);
        btnkakeru.setOnClickListener(this);
        btnwaru = findViewById(R.id.btnwaru);
        btnikoru = findViewById(R.id.btnikoru);
        btnikoru.setOnClickListener(this);
        btnwaru.setOnClickListener(this);
        btnten = findViewById(R.id.btnten);
        btnten.setOnClickListener(this);
        btnplusmainasu = findViewById(R.id.btnplusmainasu);
        btnplusmainasu.setOnClickListener(this);
        btnclear = findViewById(R.id.btnclear);
        btnclear.setOnClickListener(this);

        sb = new StringBuilder();

    }

    //数字ボタン機能
    private void numberbtnfunc(String num) {

        if (calcphase == 0) {
            calcphase = 1;
        } else if (calcphase == 2) {
            calcphase = 3;
        }

        if (sb.length() < numdigit) {
            if (sb.toString().equals("0")) {
                sb.delete(0, sb.length());
            }
            sb.append(num);
            if (calcphase == 1) {
                calctext1.setText(sb);
            } else if (calcphase == 3) {
                calctext2.setText(sb);
            }
        }

    }

    //四則記号ボタン機能
    private void sisokubtnunc(String sisoku) {
        if (calcphase != 0) {
            sisokuphase = sisoku;
            sisokutext.setText(sisokuphase);
            tencheck(calctext1);
            if (calcphase == 1) {
                calcsum = Double.parseDouble(sb.toString());
            } else if (calcphase == 3) {
                //計算処理
                calcsum = calcfanc();
                intcheck(calctext1, calcsum);
                calctext2.setText("");
            } else if (calcphase == 4) {
                //calc3を左辺に代入する
                calcsum = Double.parseDouble(calctext3.getText().toString());
                intcheck(calctext1, calcsum);
                calctext2.setText("");
                calctext3.setText("");
            }
            calcphase = 2;
            sb.delete(0, sb.length());
        }

    }

    //計算機能
    private double calcfanc() {
        double d = Double.parseDouble(sb.toString());
        if (sisokuphase.equals("+")) {
            calcsum += d;
        } else if (sisokuphase.equals("-")) {
            calcsum -= d;
        } else if (sisokuphase.equals("×")) {
            calcsum *= d;
        } else if (sisokuphase.equals("÷")) {
            if (d == 0) {
                //0で割るエラー回避
                calcsum = 0;
            } else {
                calcsum /= d;
            }
        } else {
            calcsum = d;
        }

        String s = "" + calcsum;
        if (calcsum >= maxnum(numdigit)) {
            calcsum = maxnum(numdigit) - 1;
        } else {
            calcsum = mathround(charcouter(s));
        }

        return calcsum;
    }

    private double mathround(int sum) {
        double d = 1.0;
        sum = (numdigit - 1) - sum;
        for (int i = 0; i < sum; i++) {
            d *= 10;
        }
        return (Math.round(calcsum * d)) / d;
    }

    //最大表示可能数値
    private int maxnum(int digit) {
        int i = 1;
        for (int h = 0; h < digit; h++) {
            i *= 10;
        }
        return i;
    }

    //整数チェック
    private void intcheck(TextView text, double d) {
        if (d % 1 == 0) {
            //整数の時
            text.setText(String.valueOf((int) d));
        } else {
            //少数の時
            text.setText(String.valueOf(d));
        }
    }

    //小数点が含まれているか確認
    private int charcouter(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') {
                count = i;
            }
        }

        return count;
    }

    //小数点ボタン機能
    private void tenbtnfunc() {
        if (sb.length() < (numdigit - 1)) {
            sb.append(".");
            if (calcphase == 1) {
                calctext1.setText(sb);
            } else if (calcphase == 3) {
                calctext2.setText(sb);
            }
        }
    }
    //「±」ボタン機能
    private void plusmainasubtnfunc() {
        if (sb.length() < numdigit) {
            if (!sb.toString().equals("") && !sb.toString().equals("0")) {
                if (sb.toString().substring(0, 1).equals("-")) {
                    sb.delete(0, 1);
                } else {
                    sb.insert(0, "-");
                }
                if (calcphase == 1) {
                    calctext1.setText(sb);
                } else if (calcphase == 3) {
                    calctext3.setText(sb);

                }
            }
        }
    }

    private void tencheck(TextView text) {
        if (sb.length() > 0) {
            if (sb.toString().substring(sb.length() - 1).equals(".")) {
                sb.setLength(sb.length() - 1);
                text.setText(sb);
            }
        }
    }
    //クリアボタン機能
    private void clearbtnfunc() {
        calctext1.setText("");
        calctext2.setText("");
        calctext3.setText("");
        sisokutext.setText("");
        calcphase = 0;
        sisokuphase = "";
        sb.delete(0, sb.length());
        calcsum = 0;
    }

    public void onClick(@NonNull View v) {
        if (v.getId() == R.id.btn0) {
            numberbtnfunc("0");
        } else if (v.getId() == R.id.btn1) {
            numberbtnfunc("1");
        } else if (v.getId() == R.id.btn2) {
            numberbtnfunc("2");
        } else if (v.getId() == R.id.btn3) {
            numberbtnfunc("3");
        } else if (v.getId() == R.id.btn4) {
            numberbtnfunc("4");
        } else if (v.getId() == R.id.btn5) {
            numberbtnfunc("5");
        } else if (v.getId() == R.id.btn6) {
            numberbtnfunc("6");
        } else if (v.getId() == R.id.btn7) {
            numberbtnfunc("7");
        } else if (v.getId() == R.id.btn8) {
            numberbtnfunc("8");
        } else if (v.getId() == R.id.btn9) {
            numberbtnfunc("9");
        } else if (v.getId() == R.id.btnplus) {
            sisokubtnunc("+");
        } else if (v.getId() == R.id.btnmainasu) {
            sisokubtnunc("-");
        } else if (v.getId() == R.id.btnkakeru) {
            sisokubtnunc("×");
        } else if (v.getId() == R.id.btnwaru) {
            sisokubtnunc("÷");
        } else if (v.getId() == R.id.btnikoru) {
            if (calcphase == 3) {
                calcphase = 4;
                calcfanc();
                intcheck(calctext3, calcsum);
            }
        } else if (v.getId() == R.id.btnten) {
            String s = sb.toString();
            if (!s.equals("") && charcouter(s) < 1) {
                tenbtnfunc();
            }
        } else if (v.getId() == R.id.btnplusmainasu) {
            plusmainasubtnfunc();
        } else if (v.getId() == R.id.btnclear) {
            clearbtnfunc();
        }
    }


}