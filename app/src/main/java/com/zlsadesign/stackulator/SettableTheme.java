package com.zlsadesign.stackulator;

interface SettableTheme {
  public int THEME_DEFAULT = 0;
  public int THEME_HIGH_CONTRAST = 1;

  public int THEME_TOGGLE = 30;

  public void setSettableTheme(int id);
  public int getSettableTheme();
}
