import 'package:flutter/material.dart';
import 'package:weather_app/models/weather_model.dart';

class WeatherInfoView extends StatelessWidget {
  const WeatherInfoView({super.key, required this.weatherModel});
  final WeatherModel weatherModel;

  @override
  Widget build(BuildContext context) {
    if (weatherModel.cityName == 'Israel') {
      return const Padding(
        padding: EdgeInsets.all(10.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              'there is no place called Israel try search about palastine',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
            Image(
              image: AssetImage('assets/images/palastine.jpg'),
            )
          ],
        ),
      );
    } else {
      return Container(
        decoration: BoxDecoration(
            gradient: LinearGradient(
          colors: [
            getWeatherColor(weatherModel.condition),
            getWeatherColor(weatherModel.condition)[300]!,
            getWeatherColor(weatherModel.condition)[50]!,
          ],
          begin: Alignment.topCenter,
          end: Alignment.bottomCenter,
        )),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              weatherModel.cityName,
              style: const TextStyle(
                fontSize: 36,
                fontWeight: FontWeight.bold,
              ),
            ),
            Text(
              'Last Updated at ${weatherModel.date.hour} : ${weatherModel.date.minute}',
              style: const TextStyle(color: Colors.grey),
            ),
            const SizedBox(
              height: 50,
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Image.network('https:${weatherModel.image}'),
                const SizedBox(
                  width: 50,
                ),
                Text(
                  weatherModel.temp.round().toString(),
                  style: const TextStyle(
                      fontSize: 30, fontWeight: FontWeight.bold),
                ),
                const SizedBox(
                  width: 50,
                ),
                Column(
                  children: [
                    Text(
                      'Max Temp: ${weatherModel.maxTemp.round().toString()}',
                      style: const TextStyle(color: Colors.grey),
                    ),
                    Text(
                      'Min Temp: ${weatherModel.minTemp.round().toString()}',
                      style: const TextStyle(color: Colors.grey),
                    ),
                  ],
                ),
              ],
            ),
            const SizedBox(
              height: 50,
            ),
            Text(
              weatherModel.condition,
              style: const TextStyle(
                fontSize: 36,
                fontWeight: FontWeight.bold,
              ),
            ),
          ],
        ),
      );
    }
  }
}

MaterialColor getWeatherColor(String? condition) {
  if (condition == null) {
    return Colors.blue;
  }
  if (condition == 'Sunny' || condition == 'Clear') {
    return Colors.orange;
  } else if (condition == 'Partly cloudy' || condition == 'Cloudy') {
    return Colors.cyan;
  } else if (condition == 'Overcast' ||
      condition == 'Mist' ||
      condition == 'Fog' ||
      condition == 'Freezing fog') {
    return Colors.grey;
  } else if (condition == 'Patchy rain possible' ||
      condition == 'Patchy light rain' ||
      condition == 'Light rain' ||
      condition == 'Moderate rain at times' ||
      condition == 'Moderate rain' ||
      condition == 'Heavy rain at times' ||
      condition == 'Heavy rain' ||
      condition == 'Light freezing rain' ||
      condition == 'Moderate or heavy freezing rain' ||
      condition == 'Patchy snow possible' ||
      condition == 'Patchy sleet possible' ||
      condition == 'Patchy freezing drizzle possible' ||
      condition == 'Thundery outbreaks possible' ||
      condition == 'Blowing snow' ||
      condition == 'Blizzard' ||
      condition == 'Light drizzle' ||
      condition == 'Freezing drizzle' ||
      condition == 'Heavy freezing drizzle' ||
      condition == 'Moderate or heavy sleet' ||
      condition == 'Patchy moderate snow' ||
      condition == 'Moderate snow' ||
      condition == 'Patchy heavy snow' ||
      condition == 'Heavy snow' ||
      condition == 'Ice pellets' ||
      condition == 'Light rain shower' ||
      condition == 'Moderate or heavy rain shower' ||
      condition == 'Torrential rain shower' ||
      condition == 'Light sleet showers' ||
      condition == 'Moderate or heavy sleet showers' ||
      condition == 'Light snow showers' ||
      condition == 'Moderate or heavy snow showers' ||
      condition == 'Light showers of ice pellets' ||
      condition == 'Moderate or heavy showers of ice pellets') {
    return Colors.lightBlue;
  } else if (condition == 'Patchy sleet possible' ||
      condition == 'Patchy light drizzle' ||
      condition == 'Moderate rain at times' ||
      condition == 'Moderate rain' ||
      condition == 'Moderate or heavy rain' ||
      condition == 'Patchy light snow' ||
      condition == 'Light snow' ||
      condition == 'Patchy moderate snow' ||
      condition == 'Moderate snow' ||
      condition == 'Patchy heavy snow' ||
      condition == 'Heavy snow' ||
      condition == 'Patchy light rain with thunder' ||
      condition == 'Moderate or heavy rain with thunder' ||
      condition == 'Patchy light snow with thunder' ||
      condition == 'Moderate or heavy snow with thunder') {
    return Colors.yellow;
  }
  // Add more concatenated conditions here for the remaining weather conditions

  // Default color if no condition matches
  return Colors.blue;
}
