import svelte from 'rollup-plugin-svelte';
import commonjs from 'rollup-plugin-commonjs';
import resolve from 'rollup-plugin-node-resolve';
import json from 'rollup-plugin-json';
import { scss } from "@kazzkiq/svelte-preprocess-scss";

export default {
	input: ['src/main.js', 'src/renderer.js'],

	output: {
		dir: 'dist',
		format: 'cjs',
		sourcemap: true,
	},

	plugins: [
		resolve(),
		svelte({
			css(css) {
				css.write('dist/svelte.css');
			},
			preprocess: {
				style: scss()
			}
		}),
		commonjs(),
		json()
	],

	external: [
		'electron',
		'child_process',
		'fs',
		'path',
		'url',
		'module',
		'os'
	]
};
