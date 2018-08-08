import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'dart:async';

void main() => runApp(new MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'Flutter Demo',
      theme: new ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: new MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => new _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  _MyHomePageState() {
    platform.setMethodCallHandler(_handleMethod);
  }

  static const platform = const MethodChannel('com.test/testNativeCalls');

  String results = "Before Return";

  Future<dynamic> _launchActivity() async {
    try {
      await platform.invokeMethod('launchActivity', {'input': 'a message'});
    } on PlatformException catch (e) {
      print(e.toString());
    }
  }

  Future<Null> _handleMethod(MethodCall call) async {
    switch (call.method) {
      case "activityResult":
        print(call.arguments);
        setState(() {
          results = call.arguments;
        });
        return new Future.value();
    }
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text(widget.title),
      ),
      body: new Center(
        child: new Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            new Text(
              results,
            ),
          ],
        ),
      ),
      floatingActionButton: new FloatingActionButton(
        onPressed: () {
          _launchActivity();
        },
        tooltip: 'Launch Activity',
        child: new Icon(Icons.forward),
      ),
    );
  }
}
