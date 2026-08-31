# GoreeCloud Keyboard Features

## Implemented in Development source

- Native Android input method using `InputMethodService`.
- Original first-party keyboard rendering surface.
- QWERTY letter entry.
- Shift, backspace, space, and enter controls.
- Letters/symbols mode switching with a primary digit/common-punctuation page and a second first-party symbol page for brackets, operators, currency marks, and common typographic symbols.
- Direct `ABC`, `?123`, and `=\\<` navigation between implemented input layers.
- A bounded first-party emoji layer with Smileys, People, Nature, Food, and Symbols categories plus selected composed variation-selector, skin-tone, ZWJ, family, rainbow-flag, and regional-indicator sequences.
- Compact accessible emoji-category controls with separate spoken accessibility labels.
- Bounded emoji recents that persist only in Android private app preferences, restore after an IME process restart, and can be explicitly cleared from the emoji strip.
- Fully offline transient emoji search over the packaged first-party emoji catalog; query text stays inside the IME search session and only a deliberately selected result is committed to the editor.
- Complete-String emoji commit behavior and bounded text-unit backspace tests that require every currently exposed emoji key to delete in one backspace action.
- Bounded Unicode backspace handling for combining marks, variation selectors, emoji modifiers, keycaps, tag sequences, common ZWJ emoji, CRLF, and regional-indicator runs using pair-from-the-start flag parity.
- Local-only GoreeCloud Quill suggestion boundary.
- Deterministic prefix suggestions and bounded one-edit typo-correction candidates.
- Sensitive-editor privacy gating for suggestion capture/display/acceptance and text look-behind deletion logic.
- Composing-context reset across editor transitions and non-letter layer changes.
- Light and Dark Glaze UI 2.0 source token mapping.
- No Android network permission in the current foundation; emoji recents and emoji search do not synchronize or emit telemetry.
- Unit, build, governance, and emulator validation paths, including rendered secondary-symbol hit testing.

## Development / acceptance work still required

- Complete migration and rendered acceptance against the current Stable Glaze UI contract, including applicable material roles and accessibility settings.
- Representative physical-device IME acceptance.
- Complete TalkBack, switch-access, large-text, contrast, reduced-transparency, and adaptive-layout acceptance.
- Tablet/foldable/one-handed/split keyboard experiences.
- Broader composed-sequence coverage, complete grapheme segmentation, and additional language/locale input modes.
- Signed release packaging and distribution acceptance.

## Planned product capabilities — not current implementation claims

- Gesture/swipe typing.
- Richer local prediction/correction and dictionaries.
- Multilingual input and language switching.
- User-controlled personalization and learned-language features.
- Clipboard tools with explicit privacy boundaries.
- Optional voice/input adapters where platform and privacy policies permit.
- GoreeCloud Quill writing assistance beyond the current local suggestion boundary.
- Governed synchronization, backup/recovery, and portability where explicitly implemented.

All planned capabilities remain subject to GoreeCloud privacy, security, identity, continuity, integration, design, and release acceptance requirements.
