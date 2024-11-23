import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:weather_app/cubets/wethear_cubit.dart';
import 'package:weather_app/cubets/wether_state.dart';
import 'package:weather_app/views/home_page.dart';

void main() {
  runApp(const WeatherApp());
}

class WeatherApp extends StatelessWidget {
  const WeatherApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => GetWethearCubit(),
      child: Builder(
        builder: (context) => BlocBuilder<GetWethearCubit, WeatherDataState>(
          builder: (context, state) {
            return MaterialApp(
              theme: ThemeData(
                primarySwatch:
                    BlocProvider.of<GetWethearCubit>(context).weatherModel ==
                            null
                        ? Colors.blue
                        : BlocProvider.of<GetWethearCubit>(context)
                            .weatherModel
                            ?.getWeatherColor(
                                BlocProvider.of<GetWethearCubit>(context)
                                    .weatherModel!
                                    .condition),
              ),
              // theme: ThemeData(
              //   primarySwatch: getWeatherColor(
              //       BlocProvider.of<GetWethearCubit>(context)
              //           .weatherModel
              //           ?.condition),
              // ),
              debugShowCheckedModeBanner: false,
              home: const HomePage(),
            );
          },
        ),
      ),
    );
  }
}

// MaterialColor getWeatherColor(String? condition) {
//   if (condition == null) {
//     return Colors.blue;
//   }
//   if (condition == 'Sunny' || condition == 'Clear') {
//     return Colors.orange;
//   } else if (condition == 'Partly cloudy' || condition == 'Cloudy') {
//     return Colors.cyan;
//   } else if (condition == 'Overcast' ||
//       condition == 'Mist' ||
//       condition == 'Fog' ||
//       condition == 'Freezing fog') {
//     return Colors.grey;
//   } else if (condition == 'Patchy rain possible' ||
//       condition == 'Patchy light rain' ||
//       condition == 'Light rain' ||
//       condition == 'Moderate rain at times' ||
//       condition == 'Moderate rain' ||
//       condition == 'Heavy rain at times' ||
//       condition == 'Heavy rain' ||
//       condition == 'Light freezing rain' ||
//       condition == 'Moderate or heavy freezing rain' ||
//       condition == 'Patchy snow possible' ||
//       condition == 'Patchy sleet possible' ||
//       condition == 'Patchy freezing drizzle possible' ||
//       condition == 'Thundery outbreaks possible' ||
//       condition == 'Blowing snow' ||
//       condition == 'Blizzard' ||
//       condition == 'Light drizzle' ||
//       condition == 'Freezing drizzle' ||
//       condition == 'Heavy freezing drizzle' ||
//       condition == 'Moderate or heavy sleet' ||
//       condition == 'Patchy moderate snow' ||
//       condition == 'Moderate snow' ||
//       condition == 'Patchy heavy snow' ||
//       condition == 'Heavy snow' ||
//       condition == 'Ice pellets' ||
//       condition == 'Light rain shower' ||
//       condition == 'Moderate or heavy rain shower' ||
//       condition == 'Torrential rain shower' ||
//       condition == 'Light sleet showers' ||
//       condition == 'Moderate or heavy sleet showers' ||
//       condition == 'Light snow showers' ||
//       condition == 'Moderate or heavy snow showers' ||
//       condition == 'Light showers of ice pellets' ||
//       condition == 'Moderate or heavy showers of ice pellets') {
//     return Colors.lightBlue;
//   } else if (condition == 'Patchy sleet possible' ||
//       condition == 'Patchy light drizzle' ||
//       condition == 'Moderate rain at times' ||
//       condition == 'Moderate rain' ||
//       condition == 'Moderate or heavy rain' ||
//       condition == 'Patchy light snow' ||
//       condition == 'Light snow' ||
//       condition == 'Patchy moderate snow' ||
//       condition == 'Moderate snow' ||
//       condition == 'Patchy heavy snow' ||
//       condition == 'Heavy snow' ||
//       condition == 'Patchy light rain with thunder' ||
//       condition == 'Moderate or heavy rain with thunder' ||
//       condition == 'Patchy light snow with thunder' ||
//       condition == 'Moderate or heavy snow with thunder') {
//     return Colors.yellow;
//   }
//   // Add more concatenated conditions here for the remaining weather conditions

//   // Default color if no condition matches
//   return Colors.blue;
// }
// // b95866e69727e678805876a71999d958
