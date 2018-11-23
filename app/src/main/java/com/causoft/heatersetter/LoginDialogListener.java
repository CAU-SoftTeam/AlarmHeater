package com.causoft.heatersetter;



public interface LoginDialogListener {
   void onPositiveClicked(String id, String pw);
   void onNegativeClicked();
}