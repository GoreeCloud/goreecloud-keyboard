# GoreeCloud Keyboard Features

## Implemented in Development source

- Native Android input method using `InputMethodService`.
- Original first-party keyboard rendering, hit-testing, and pointer-input surface.
- Runtime input-method registration checks through Android `InputMethodManager`.
- QWERTY letter entry with shift, backspace, space, and editor-action controls.
- A dedicated 1–0 number row on the letters layer, enabled by default and user-controllable from the Android IME settings surface through a device-local Boolean presentation preference.
- A configurable native utility toolbar that contains only real implemented controls: Emoji, Symbols, and Keyboard Settings. The toolbar master control and each action are independently user-configurable through device-local Boolean presentation preferences; if no actions are enabled, the toolbar collapses rather than rendering placeholders.
- Toolbar Emoji and Symbols actions reuse the existing first-party layer-routing path; the Settings action routes through the IME service to the existing Keyboard settings activity. Toolbar controls use the existing Glaze interaction floor, press treatment, virtual accessibility-node path, resource-backed accessibility labels, and selected-state semantics for active Emoji/Symbols layers.
- Adaptive editor action presentation for Enter, Go, Search, Send, Next, Done, and Previous host semantics, with semantic accessibility labels and `performEditorAction()` dispatch for explicit host actions plus ordinary Enter fallback.
- Letters/symbols mode switching with a primary digit/common-punctuation page and a second first-party symbol page for brackets, operators, currency marks, and common typographic symbols.
- Direct `ABC`, `?123`, and `=\\<` navigation between implemented input layers.
- A bounded first-party emoji layer with Smileys, People, Nature, Food, Symbols, and Travel categories plus selected composed variation-selector, skin-tone, ZWJ, family, rainbow-flag, and regional-indicator sequences.
- Compact accessible emoji-category controls with separate spoken accessibility labels.
- Bounded emoji recents stored only in Android private app preferences, restored after an IME process restart, and explicitly clearable from the emoji strip.
- Fully offline transient emoji search over the packaged first-party catalog; query text stays inside the IME search session and only a deliberately selected result is committed to the editor.
- Complete-String emoji commit behavior and bounded Unicode-aware backspace for combining marks, variation selectors, emoji modifiers, keycaps, tag sequences, common ZWJ emoji, CRLF, and regional-indicator runs.
- Fail-closed handling when bounded ordinary-field look-behind may begin inside a larger text unit; sensitive fields retain the stricter no-look-behind path.
- Local-only GoreeCloud Quill suggestion boundary with deterministic prefix candidates, bounded typo correction, bounded transient capture, and exact presented-candidate commit authority.
- Sensitive-editor privacy gating for suggestion capture/display/acceptance and text look-behind deletion logic.
- Host `TYPE_TEXT_FLAG_NO_SUGGESTIONS` support without misclassifying ordinary fields as passwords.
- Fail-closed editor lifecycle behavior across authoritative Android start/finish callbacks, including conservative no-active-editor and missing-editor-metadata state.
- Deterministic local long-press alternates for common Latin diacritics and punctuation.
- Viewport-bounded long-press popup placement, shared render/hit-test geometry, pointer movement selection, cancellation, haptic feedback, and accessibility semantics.
- A privacy-minimized `goreecloud-keyboard-preferences/1` format containing exactly the last explicitly selected emoji category.
- Strict portable-preference validation/checksum integrity, category-only reader/writer seams, explicit user-controlled Android Storage Access Framework transfer, import preview before mutation, and export review/freeze before destination selection.
- A separate device-local keyboard presentation store for the number-row Boolean plus utility-toolbar visibility/action Booleans. It does not contain typed text, composing context, suggestions, clipboard content, credentials, editor content, or usage-derived history and is intentionally outside the portable preference format.
- GLAZE UI V1.3 / `1.3.0` Development mapping pinned to exact Stable integration revision `fc7cc91d2eace8da2371371c2855c24cbcb326a1` through the current stacked migration work.
- Frosted Neutral key surfaces, Light/Dark runtime selection, explicit Deep Dark source values, 48/56 dp interaction floors, control geometry, and deterministic state calibration inherited into the current V1.3 Development mapping.
- V1.3 Adaptive Resonance authority boundaries that keep typed/editor content outside color derivation and enable no environmental memory, remote color derivation, persistent sample history, semantic inference, telemetry, network lookup, or animated atmosphere.
- No Android network permission in the current foundation; emoji recents, emoji search, suggestions, alternates, local layout/toolbar preferences, and portable preference transfer do not synchronize or emit telemetry.
- Unit, build, governance, and Android emulator validation paths covering registration, privacy lifecycle, Unicode deletion, suggestion authority, emoji search, portable preference boundaries, Glaze UI mapping, native interaction, the dedicated number row, configurable utility toolbar, adaptive action semantics, and accessibility state/action boundaries.

## Development / acceptance work still required

- Complete rendered/native consumer acceptance against current GLAZE UI V1.3 across all keyboard and settings surfaces, including the utility-toolbar row and expanded settings content.
- Reduced Transparency / solid fallback, Reduced Motion, Increased Contrast, forced-colors/native equivalents, 200% text/reflow, runtime Touch Assistance, RTL/localization, and Deep Dark runtime policy where applicable.
- Representative physical-device IME acceptance across supported Android/editor combinations.
- Complete TalkBack, Switch Access, Voice Access, Touch Assistance, and other claimed assistive-input acceptance, including toolbar navigation/state behavior.
- Representative phone/tablet/foldable ergonomics, including one-handed/split/adaptive layouts where implemented.
- Representative physical-device latency, performance, power, and thermal acceptance.
- Human Visual Excellence review of the actual Keyboard consumer, including the five-row number-row layout, utility toolbar, suggestion strip, and adaptive action-key states.
- Broader composed-sequence coverage, complete grapheme segmentation, and additional language/locale input modes.
- Privacy Shield, Wardveil Security, Everkeep, Manager, Mesh, and Identity acceptance where applicable without expanding input-data authority.
- Complete approved backup/clean-target recovery scope beyond the current one-field portability primitive.
- Protected signing/provenance, release packaging, distribution, update/rollback, and explicit Stable approval.

## Approved inspiration goals — planned, not current implementation claims

The supplied Android-keyboard inspiration references establish the following active GoreeCloud product obligations. They remain Planned until implemented and accepted:

- GoreeCloud Secure Paste, clipboard history, and pinned snippets with explicit Privacy Shield enforcement and user control.
- GIF and sticker discovery through approved content/privacy/security boundaries.
- Privacy-approved voice input.
- Translation tools with explicit data-flow and consent boundaries.
- Gesture/swipe typing.
- Cursor-control and text-selection gestures.
- Stronger local prediction/correction and user/language dictionaries.
- Multilingual input and explicit language switching.
- User-controlled personalization and learned-language features where separately approved.
- Manual Light/Dark appearance overrides and broader theme controls beyond current system Light/Dark adaptation.
- One-handed, floating, split, tablet, foldable, and posture-aware keyboard experiences.
- Richer emoji, symbol, kaomoji, and specialized input discovery.
- GoreeCloud Quill writing assistance beyond the current local suggestion boundary.
- Additional governed Launcher, Search, and other GoreeCloud ecosystem actions through explicit platform contracts. New toolbar actions remain Planned until their underlying capability exists; a button alone is not implementation.
- Governed synchronization, backup/recovery, and portability where explicitly implemented.

All planned capabilities remain subject to GoreeCloud privacy, security, identity, continuity, integration, design, accessibility, and release acceptance requirements. Buttons, labels, placeholders, or decorative surfaces do not count as implementation evidence by themselves.
