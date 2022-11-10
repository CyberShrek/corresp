<!-- Drops a floating notation exactly where the <Notation> component was added-->

<script>
    import { onMount } from "svelte"
    import { blur, slide } from "svelte/transition"

    export let text, type, size

    $: background = type==="error"   ? "red"
                  : type==="warning" ? "darkkhaki"
                  : "lightskyblue"

    let render, top, width

    onMount(() => {
        let nearestElem = render.previousElementSibling,
            parentElem = render.parentElement
        if (!nearestElem) nearestElem = parentElem
        top   = nearestElem.offsetTop + nearestElem.clientHeight + "px"
        width = parentElem.clientWidth - 10 + "px"
    })
</script>

<dropdown bind:this={render}
   transition:blur
   style="background:{background}; color: white; top:{top}; width:{width}; font-size:{size}">
    {text}
</dropdown>