import {
	StyleSheet,
	Dimensions
} from 'react-native';

const {
	width
} = Dimensions.get('window');

export default StyleSheet.create({
	root: {
		flex: 1,
		backgroundColor: '#f2f2f2',
		justifyContent: 'center',
		alignItems: 'center',
		padding: width * 0.05
	},



	centerSection: {
		flex: 1,
		alignItems: 'center',
		justifyContent: 'center'
	},

	title: {
		fontSize: width * 0.05,
		color: 'rgb(30, 30, 30)',
	},

	description: {
		fontSize: width * 0.08,
		color: 'rgb(30, 30, 30)',
	},

	button: {
		backgroundColor: '#3cba54',
		width: width * 0.75,
		alignItems: 'center',
		marginBottom: 10,
		marginTop: 5
	},

	buttonFail: {
		backgroundColor: 'rgb(255, 68, 68)',
		width: width * 0.75,
		alignItems: 'center',
	},

	buttonText: {
		fontSize: width * 0.05,
		color: '#f2f2f2',
		paddingVertical: width * 0.02

	},
});