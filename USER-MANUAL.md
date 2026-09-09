# GoreeCloud Keyboard User Manual

## Current availability

GoreeCloud Keyboard is currently a **Development** Android input-method implementation. This manual describes behavior present in the current Development source stack. It does not claim a public production release, Stable qualification, representative physical-device acceptance, or a current Apple-platform build.

## Enable the keyboard on Android

After installing a Development build, use Android's system keyboard/input-method settings to enable **GoreeCloud Keyboard**. Android may show a standard warning when enabling any third-party input method; review the system prompt and enable the keyboard only if you intend to use it.

Use Android's keyboard switcher or input-method selector to choose GoreeCloud Keyboard when a text field is active. Exact settings labels vary by Android device and version.

## Type letters and use the number row

The keyboard opens in its **letters** layer. The current inspiration-driven layout shows a dedicated **1–0 number row** above QWERTY by default.

- Tap number or letter keys to enter text.
- Tap **⇧** to shift the next alphabetic character.
- Tap **⌫** to delete the preceding text unit supported by the current deletion model.
- Tap **space** to insert a space.
- Use the right-side action key to perform the active editor action or ordinary Enter.

The temporary shift state resets after a shifted alphabetic character is entered. Digits are not affected by shift.

### Show or hide the number row

Open the Android settings entry for **GoreeCloud Keyboard settings** and change **Show number row**. The choice is stored only as a device-local Boolean presentation preference. It applies the next time the keyboard input view opens.

The number-row choice is not included in the current portable preference export.

## Adaptive action key

The right-side action key adapts to Android editor metadata when the host field requests a supported action. Depending on the field, it can represent:

- Enter;
- Go;
- Search;
- Send;
- Next;
- Done; or
- Previous.

The accessibility label follows the semantic action. When Android does not provide an applicable explicit editor action, the keyboard uses ordinary Enter. If a host declines an explicit editor action, the current service falls back to the Enter-key path.

## Type numbers and symbols

Tap **?123** from the letters layer to open the primary symbols page. It includes digits and common punctuation even when the dedicated letters-layer number row is disabled.

From the primary symbols page:

- tap **ABC** to return directly to letters;
- tap **=\\<** to open the secondary symbols page.

The secondary page adds brackets, operators and separators, currency marks, and common typographic symbols. From that page, tap **ABC** to return directly to letters or **?123** to return to the primary symbols page.

Changing layers clears the temporary word context used for local suggestions. Symbol input is not added to that composing-word context.

## Emoji

Tap **☺** from the letters or symbols layers to open the bounded local emoji surface. The compact category strip exposes Smileys, People, Nature, Food, Travel, and Symbols with spoken accessibility labels. Emoji keys commit their complete Unicode `String` value, including supported multi-code-point sequences such as skin-tone variants, ZWJ sequences, flags, and variation-selector forms.

After you commit at least one emoji, a **Recent** control appears. Current recents behavior is intentionally privacy-bounded:

- the most recently committed emoji is promoted to the front;
- selecting an emoji already in the list promotes it rather than creating a duplicate;
- at most 24 exact emoji String values are retained;
- recents are stored only in Android private application preferences for GoreeCloud Keyboard;
- recents survive an IME process restart on the same Android app installation;
- recents are not synchronized, transmitted, logged, or used to build a learned-use profile; and
- the visible Clear behavior removes the stored local recents list as well as the in-memory list.

The current emoji search is fully offline and searches only the packaged first-party catalog. Search-query text remains transient IME navigation state and is not committed into the active editor unless you explicitly choose an emoji result.

The current picker is still a bounded Development catalog rather than a claim of a complete emoji/GIF/sticker platform.

## Long-press alternate characters

Eligible letter and punctuation keys expose deterministic local alternates after a long press. While the popup is open, move to the desired alternate and release to insert it. Popup placement and hit testing are bounded to the visible keyboard viewport.

The current source also exposes alternate-character actions through the native virtual accessibility control model where implemented in the active Development stack.

## Local GoreeCloud Quill suggestions

For ordinary text fields, the suggestion strip can show local candidates derived from the current composing word and the keyboard's local Development dictionary.

Tap a suggestion to replace the current composing prefix with that suggestion followed by a space.

The current suggestion engine is intentionally bounded. It provides deterministic prefix candidates and limited typo-correction candidates; it is not a claim of a complete language model, cloud writing service, or full autocorrect system.

## Sensitive text fields

For editor types classified as sensitive, GoreeCloud Keyboard suppresses suggestion collection, display, and acceptance and clears transient composing context at editor transitions. Backspace also avoids text look-behind in those sensitive editors.

This is a Development privacy boundary, not a claim that the keyboard can independently verify every application's semantic use of a text field. Android's editor metadata remains part of the classification signal.

## Portable emoji-category preference

The Keyboard settings surface also contains the existing explicit import/export flow for the last emoji category you deliberately selected.

That portable format contains only the selected emoji category. It does **not** include typed text, emoji recents, search queries, suggestions, learned input, clipboard data, credentials, sensitive editor content, or the number-row preference. Export and import use Android's Storage Access Framework so you explicitly choose the document source or destination; import is previewed before applying and export is reviewed/frozen before the destination is chosen.

## Network behavior

The current Android application foundation does **not** request Android network permission. Current Quill suggestions, emoji categories/recents/search, long-press alternates, and the number-row preference are local-only.

Future network-backed capabilities, if implemented, require separate user-control, Privacy Shield, security, identity, and acceptance work and must be documented before they can be treated as current behavior.

## Appearance

The current stacked Development work targets GLAZE UI V1.3 / `1.3.0` using exact Stable integration revision `fc7cc91d2eace8da2371371c2855c24cbcb326a1`. This mapping remains Development evidence rather than complete Keyboard Stable acceptance.

The live keyboard currently follows Android Light/Dark night mode using the Glaze-derived material foundation. Deep Dark source values exist in the current migration stack, but the IME does not automatically select Deep Dark and this tranche does not add a manual appearance selector.

V1.3 Adaptive Resonance does not authorize typed/editor-content color sampling. Current Keyboard source does not add editor-driven color extraction, environmental memory, remote derivation, persistent sample history, semantic inference, telemetry, or animated atmosphere.

Complete rendered/accessibility GLAZE UI V1.3 acceptance, runtime Deep Dark policy, Reduced Transparency/Motion, Increased Contrast/native equivalents, large text/reflow, Touch Assistance, RTL/localization, TalkBack/Switch Access/Voice Access, adaptive/form-factor validation, representative physical-device acceptance, Human Visual Excellence review, and production design acceptance remain incomplete.

## Inspiration-driven capabilities still planned

The approved input-surface direction includes several features that are **not current implementation claims**:

- configurable utility toolbar;
- GoreeCloud Secure Paste, clipboard history, and pinned snippets;
- GIF and sticker discovery;
- voice input;
- translation;
- gesture/swipe typing;
- cursor-control and text-selection gestures;
- multilingual layouts and language switching;
- stronger dictionaries, correction, prediction, and user-controlled personalization;
- manual Light/Dark appearance overrides and broader themes;
- one-handed, floating, split, tablet, foldable, and posture-aware layouts;
- broader Quill-assisted writing; and
- governed Launcher/Search/other GoreeCloud ecosystem actions.

Placeholder buttons or decorative icons do not count as those features being implemented.

## Current limitations

The Development implementation does not yet claim complete gesture typing, multilingual input, clipboard/Secure Paste tools, voice or translation, GIF/sticker content, one-handed/floating/split layouts, full tablet/foldable adaptation, complete accessibility acceptance, user dictionary synchronization, complete Unicode grapheme segmentation for every script, signed production packaging, or Stable release acceptance.

## Privacy and security expectations

Do not interpret the absence of network permission as proof that every future keyboard feature is automatically safe. New content sources, downloadable dictionaries/models, synchronization, clipboard access, voice adapters, translation, account-backed personalization, or broader persisted usage history require their own GoreeCloud privacy, security, identity, continuity, and integration boundaries before production use.
