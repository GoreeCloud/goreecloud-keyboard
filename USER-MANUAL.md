# GoreeCloud Keyboard User Manual

## Current availability

GoreeCloud Keyboard is currently a **Development** Android input-method implementation. This manual describes behavior present in the repository source and Development builds. It does not claim a public production release, Stable qualification, representative physical-device acceptance, or current Apple-platform build.

## Enable the keyboard on Android

After installing a Development build, use Android's system keyboard/input-method settings to enable **GoreeCloud Keyboard**. Android may show a standard warning when enabling any third-party input method; review the system prompt and enable the keyboard only if you intend to use it.

Use Android's keyboard switcher or input-method selector to choose GoreeCloud Keyboard when a text field is active.

Exact settings labels vary by Android device and version.

## Type letters

The keyboard opens in its **letters** layer.

- Tap letter keys to enter text.
- Tap **⇧** to shift the next alphabetic character.
- Tap **⌫** to delete the preceding character.
- Tap **space** to insert a space.
- Tap **↵** to send the Android Enter key action to the active editor.

The temporary shift state resets after a shifted alphabetic character is entered.

## Type numbers and symbols

Tap **?123** from the letters layer to open the primary symbols page. It includes digits and common punctuation.

From the primary symbols page:

- tap **ABC** to return directly to letters;
- tap **=\\<** to open the secondary symbols page.

The secondary page adds brackets, operators and separators, currency marks, and common typographic symbols. From that page, tap **ABC** to return directly to letters or **?123** to return to the primary symbols page.

Changing layers clears the temporary word context used for local suggestions. Symbol input is not added to that composing-word context.

## Local GoreeCloud Quill suggestions

For ordinary text fields, the suggestion strip can show local candidates derived from the current composing word and the keyboard's local Development dictionary.

Tap a suggestion to replace the current composing prefix with that suggestion followed by a space.

The current suggestion engine is intentionally bounded. It provides deterministic prefix candidates and limited typo-correction candidates; it is not a claim of a complete language model, cloud writing service, or full autocorrect system.

## Sensitive text fields

For editor types classified as sensitive, GoreeCloud Keyboard suppresses suggestion collection, display, and acceptance and clears transient composing context at editor transitions.

This is a Development privacy boundary, not a claim that the keyboard can independently verify every application's semantic use of a text field. Android's editor metadata remains part of the classification signal.

## Network behavior

The current Android application foundation does **not** request Android network permission. Current Quill suggestions are local-only.

Future network-backed capabilities, if implemented, require separate user-control, Privacy Shield, security, identity, and acceptance work and must be documented before they can be treated as current behavior.

## Appearance

The current native surface uses a Glaze UI 2.0.0 Adoption Candidate mapping for Light and Dark appearance source values. Android night mode selects the current Light/Dark mapping at draw time.

Complete rendered Glaze UI conformance, Deep Dark, accessibility configurations, representative physical-device acceptance, and production design acceptance remain incomplete.

## Current limitations

The Development implementation does not yet claim complete gesture typing, multilingual input, emoji surfaces, clipboard tools, voice input, one-handed/split layouts, full tablet/foldable adaptation, complete accessibility acceptance, user dictionary synchronization, signed production packaging, or Stable release acceptance.

## Privacy and security expectations

Do not interpret the absence of network permission as proof that every future keyboard feature is automatically safe. New content sources, downloadable dictionaries/models, synchronization, clipboard access, voice adapters, or account-backed personalization require their own GoreeCloud privacy, security, identity, continuity, and integration boundaries before production use.
