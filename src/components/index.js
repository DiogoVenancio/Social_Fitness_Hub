import React, { Component } from 'react';
import {
	View,
	Text,
	StatusBar,
	NativeModules,
	TouchableOpacity,
} from 'react-native';
import Styles from './styles';

const { MyNativeModule } = NativeModules;

class ChallengesApp extends Component {

	constructor(props) {
		super(props);

		this.state = {
			level : 1,
			multiplier : .25,
			exercises: {
				flexoes: {
					base: 10,
					level: 0
				},
				abdominais: {
					base: 15,
					level: 0
				},
				elevacoes: {
					base: 5,
					level: 0
				},
				agaixamentos: {
					base: 10,
					level: 0
				},
				burpees: {
					base: 7,
					level: 0
				},
			}
		}

		this.state.chosenExercise = Object.keys(this.state.exercises)[Math.floor(Math.random() * 5)]
	}

	componentWillMount() {
	}

	componentWillUnmount() {
	}

	downgrade = () => {
		let exercise = this.state.exercises[this.state.chosenExercise];
		if(exercise.level > 1) {	
			this.state.exercises[this.state.chosenExercise].level--;
			this.setState({
				exercises: this.state.exercises,
				chosenExercise: Object.keys(this.state.exercises)[Math.floor(Math.random() * 5)]
			});
		}
	}

	upgrade = () => {	
		this.state.exercises[this.state.chosenExercise].level++;
		this.setState({
			exercises: this.state.exercises,
			chosenExercise: Object.keys(this.state.exercises)[Math.floor(Math.random() * 5)]
		});	
	}

	render() {
		let {multiplier} = this.state;
		let reps = function(exercise) {
			return Math.ceil(exercise.level * multiplier * exercise.base) + exercise.base;
		};
		return (
			<View style={Styles.root}>
				<StatusBar barStyle='dark-content' backgroundColor='#f2f2f2' />

				<View style={Styles.centerSection}>
					<Text style={Styles.title}>Random Challenge</Text>
					<Text style={Styles.description}>Realizar {`${reps(this.state.exercises[this.state.chosenExercise])}`} repetições do exercício {`${this.state.chosenExercise}`}</Text>
				</View>

				<TouchableOpacity style={Styles.buttonFail} onPress={this.downgrade}>
					<Text style={Styles.buttonText}>Não Concluído</Text>
				</TouchableOpacity>
				<TouchableOpacity style={Styles.button} onPress={this.upgrade}>
					<Text style={Styles.buttonText}>Concluído</Text>
				</TouchableOpacity>

			</View>
		);
	}
}

export default ChallengesApp;
