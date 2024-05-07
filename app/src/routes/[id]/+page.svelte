<script lang="ts">
	import { Button, Card, Heading, P } from 'flowbite-svelte';

	/** @type {import('./$types').PageData} */
	export let data;
</script>

<div>
	<Card>
		<h1 class="text-2xl font-bold">{data.name}</h1>
		<p class="mb-4">{data.address}</p>
		{#each data.openingHours as opensAt}
			<div class="mb-2">
				<p>
					{opensAt.days
						.map((s) => s.charAt(0).toUpperCase() + s.slice(1, 3).toLowerCase())
						.join(', ')}
				</p>
				{#each opensAt.timeSlots as timeSlot}
					<p>{timeSlot.start} - {timeSlot.end}</p>
				{/each}
			</div>
		{/each}
	</Card>
</div>
