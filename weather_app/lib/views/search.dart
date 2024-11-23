import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:weather_app/cubets/wethear_cubit.dart';

class SearchView extends StatelessWidget {
  const SearchView({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Search a City'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Center(
          child: TextField(
            onSubmitted: (value) {
              var getWeatherCubet = BlocProvider.of<GetWethearCubit>(context);
              getWeatherCubet.getweather(cityName: value);
              Navigator.pop(context);
            },
            decoration: const InputDecoration(
              hintText: 'Enter City Name',
              border: OutlineInputBorder(),
              suffixIcon: Icon(
                Icons.search,
              ),
              labelText: 'search',
            ),
          ),
        ),
      ),
    );
  }
}
