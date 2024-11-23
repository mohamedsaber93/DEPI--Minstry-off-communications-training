import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:weather_app/cubets/wethear_cubit.dart';
import 'package:weather_app/cubets/wether_state.dart';
import 'package:weather_app/views/no_weather.dart';
import 'package:weather_app/views/search.dart';
import 'package:weather_app/views/wearher_info.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.cyanAccent,
        title: const Text('Weather App'),
        actions: [
          IconButton(
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => const SearchView(),
                ),
              );
            },
            icon: const Icon(Icons.search),
          )
        ],
      ),
      body: BlocBuilder<GetWethearCubit, WeatherDataState>(
        builder: (context, state) {
          if (state is WeatherInitialState) {
            return const NoWheather();
          } else if (state is WethearLoadedState) {
            return WeatherInfoView(
              weatherModel: state.weatherModel,
            );
          } else {
            return const Text('oops there Was an Error');
          }
        },
      ),
    );
  }
}
