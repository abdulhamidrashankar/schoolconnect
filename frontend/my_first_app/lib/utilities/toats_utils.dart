import 'package:fluttertoast/fluttertoast.dart';
import 'package:flutter/material.dart';

class ToastUtils {
  static void show(String msg, {
    Color bgColor = Colors.black,
    ToastGravity gravity = ToastGravity.CENTER,
    int durationInSec = 3,
  }) {
    Fluttertoast.showToast(
      msg: msg,
      toastLength: Toast.LENGTH_LONG,
      gravity: gravity,
      backgroundColor: bgColor, // works on Android
      textColor: Colors.white,
      fontSize: 16.0,
      timeInSecForIosWeb: durationInSec,
      webBgColor: "#${bgColor.value.toRadixString(16).substring(2)}", // for Web
      webPosition: "center", // for Web
    );
  }
}