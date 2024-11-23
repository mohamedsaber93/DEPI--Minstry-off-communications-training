import 'package:flutter/material.dart';

class NoWheather extends StatelessWidget {
  const NoWheather({super.key});

  @override
  Widget build(BuildContext context) {
    return const Padding(
      padding: EdgeInsets.all(15.0),
      child: Center(
        child: Text(
          'There is No Whether Tap Search Button 🔎 and start searching now',
          style: TextStyle(
              color: Colors.black, fontSize: 20, fontWeight: FontWeight.bold),
        ),
      ),
    );
  }
}
