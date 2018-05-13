package br.com.avana.tabajara.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MaskUtil {

    private static final String CPF_MASK = "###.###.###-##";
    private static final String PHONE_MASK = "(##)#####-####";
    private static final String CEP_MASK = "#####-###";

    private static int CPF_LENGTH = 11;
    private static int PHONE_LENGTH = 11;
    private static int CEP_LENGTH = 8;


    public static String unmask(String input){
        return input.replaceAll("[^0-9a-zA-z]*", "");
    }

    public static String masker(String content, MaskType masktype){

        String mask = "";
        String contentFinal = "";
        String contentAux = content.replaceAll("[^0-9a-zA-z]*", "");

        switch (masktype){
            case CPF_MASK_TYPE:
                if (contentAux.length() != CPF_LENGTH){ return contentAux; }
                mask = CPF_MASK;
                break;

            case CEP_MASK_TYPE:
                if (contentAux.length() != CEP_LENGTH){ return contentAux; }
                mask = CEP_MASK;
                break;

            case PHONE_MASK_TPYE:
                if (contentAux.length() != PHONE_LENGTH) { return contentAux; }
                mask = PHONE_MASK;
                break;
        }

        int i = 0;
        for (char c: mask.toCharArray()) {
            if (c != '#'){
                contentFinal += c;
                continue;
            } else {
                contentFinal += contentAux.charAt(i);
            }
            i++;
        }
        return contentFinal;
    }

    public static TextWatcher insert(final EditText input, final MaskType maskType){
        return new TextWatcher() {

            boolean isUpdating;
            String oldValue = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = MaskUtil.unmask(s.toString());
                String mask = "";

                switch (maskType){

                    case CPF_MASK_TYPE:
                        mask = CPF_MASK;
                        break;

                    case PHONE_MASK_TPYE:
                        mask = PHONE_MASK;
                        break;

                    case CEP_MASK_TYPE:
                        mask = CEP_MASK;
                        break;
                }

                String maskAux = "";
                if (isUpdating){
                    oldValue = value;
                    isUpdating = false;
                    return;
                }

                int i = 0;
                for (char c : mask.toCharArray()) {
                    if ((c != '#' && value.length() > oldValue.length()) || (c != '#' && value.length() < oldValue.length() && value.length() != i)){
                        maskAux += c;
                        continue;
                    }
                    try {
                        maskAux += value.charAt(i);
                    } catch (Exception e){
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                input.setText(maskAux);
                input.setSelection(maskAux.length());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}

        };
    }

}
