# Keyboard Inspiration Input-Surface Development Record

Date: 2026-09-08
Status: Development

## Purpose

Record the user-approved GoreeCloud Keyboard input-surface direction derived from the supplied Android-keyboard inspiration references, distinguish inspiration from copied product design, and track which parts are implemented versus still planned.

The supplied references are design inspiration only. GoreeCloud Keyboard remains an original first-party implementation governed by GoreeCloud design, privacy, security, accessibility, continuity, and release requirements.

## Approved direction

The target experience combines:

- a clean, spacious QWERTY surface with rounded Glaze UI key geometry;
- an optional dedicated 1–0 number row;
- a configurable utility toolbar containing only real implemented/governed actions;
- a local suggestion strip above the key field;
- an adaptive editor action key for Enter, Go, Search, Send, Next, Done, and Previous semantics;
- direct symbols and emoji access with long-press alternates;
- Light/Dark system-adaptive presentation as the current runtime baseline;
- future toolbar expansion only after underlying capabilities such as Secure Paste/clipboard actions, GIF/sticker discovery, voice input, translation, and approved GoreeCloud actions actually exist under their governing boundaries;
- future compact, one-handed, floating, split, tablet, foldable, and posture-aware layouts;
- future gesture/swipe typing and cursor/text-selection gestures;
- future multilingual input, language switching, stronger local prediction/correction, dictionaries, and user-controlled personalization;
- privacy-first processing, with local operation by default and explicit approval/controls before any remote or sensitive-data capability; and
- deeper GoreeCloud integration only through approved platform contracts rather than hidden coupling.

## Implemented Development tranches

### 1. Dedicated number row

- The native letters surface can render a separate 1–0 row above QWERTY.
- It is enabled by default for the inspiration-driven layout.
- A device-local **Show number row** setting can disable or re-enable it.
- The preference contains only the Boolean presentation choice and is not added to the portable preference format.

### 2. Adaptive editor action key

- The native action key reflects Android `EditorInfo` action semantics.
- Supported presentations are Go, Search, Send, Next, Done, Previous, and ordinary Enter.
- The accessibility label follows the semantic action rather than relying on the visual glyph alone.
- The service uses `InputConnection.performEditorAction()` for explicit host actions and falls back to the ordinary Enter key path when no action is applicable or the host does not handle it.

### 3. Input-surface sizing

- The preferred native keyboard height expands when the dedicated number row is enabled so the additional row does not silently shrink the designed key field into a denser layout.
- Row spacing is derived from the actual row count.

### 4. Configurable real-action utility toolbar

The next bounded Development tranche implements the previously planned toolbar as functional behavior rather than placeholder chrome.

- The toolbar is a separate Glaze-sized row above the existing suggestion/navigation strip.
- Current real actions are **Emoji**, **Symbols**, and **Keyboard Settings** only.
- Emoji routes through the existing first-party emoji layer and exits transient emoji-search query mode when the toolbar action is explicitly used to return to normal emoji navigation.
- Symbols routes through the existing primary symbol layer.
- Settings routes through `KeyboardView.Listener` to `KeyboardService`, which opens the existing explicit Keyboard settings activity. The custom-drawn view does not gain generic activity-launch authority.
- Emoji and Symbols expose selected-state semantics when their corresponding layer is active.
- Toolbar controls are exposed through the existing native virtual accessibility-node system with resource-backed labels.
- Toolbar controls use the existing Frosted Neutral key surface/stroke/pressed treatment and the current general interaction floor.
- Toolbar master visibility plus per-action Emoji/Symbols/Settings choices are stored as device-local Boolean presentation preferences in the existing private keyboard-settings store.
- If no real actions are enabled, the toolbar collapses instead of presenting an empty row or decorative controls.
- Toolbar preferences remain outside `goreecloud-keyboard-preferences/1` and introduce no typed-text, clipboard, editor-content, learned-input, usage-history, network, account, telemetry, or synchronization authority.

### 5. Settings surface

- Android IME settings expose the number-row choice and the utility-toolbar master/per-action choices while preserving the existing bounded emoji-category import/export flow.
- The settings surface is scrollable so the expanded Development configuration does not rely on a single-screen height assumption.
- No network, clipboard, typed-text, learned-input, account, or telemetry authority is added.

## Existing behavior aligned with the inspiration direction

The current Development stack already provides first-party rounded Glaze key surfaces, Light/Dark Android night-mode adaptation, a local Quill suggestion strip, symbols pages, bounded emoji categories/recents/offline search, local long-press alternates, haptic feedback, and native virtual accessibility controls.

## Still planned — not implementation claims

The following approved inspiration goals remain active product obligations and are not represented as implemented by the current bounded toolbar tranche:

- Secure Paste/clipboard history and pinned snippets;
- GIF and sticker discovery;
- voice input;
- translation;
- gesture/swipe typing;
- cursor and selection gestures;
- multilingual layouts and language switching;
- manual Light/Dark overrides and broader appearance controls beyond current system adaptation;
- one-handed, floating, split, tablet, foldable, and posture-aware layouts;
- richer local correction/prediction and dictionaries;
- approved Quill writing assistance beyond the current local suggestion boundary; and
- additional Launcher/Search/other GoreeCloud ecosystem actions through explicit governed integration contracts.

The toolbar itself does not satisfy any future action merely by existing. Each added action must have functional behavior, privacy/security authority, accessibility semantics, tests, lifecycle integration, and appropriate runtime acceptance before it can be treated as implemented.

## Acceptance boundary

This Development work does not establish Human Visual Excellence, representative physical-device ergonomics, TalkBack/Switch Access/Voice Access acceptance, Touch Assistance, large-text/reflow, RTL/localization, tablet/foldable acceptance, latency/performance/power acceptance, Privacy Shield/Wardveil/Everkeep acceptance, Release Candidate qualification, signing/distribution acceptance, or Stable status.
