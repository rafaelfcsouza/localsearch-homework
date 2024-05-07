/** @type {import('./$types').PageLoad} */
export async function load({ params }) {
	const response = await fetch(`http://localhost:8080/${params.id}`);
	return await response.json();
}
