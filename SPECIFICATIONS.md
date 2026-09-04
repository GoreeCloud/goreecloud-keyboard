# GoreeCloud Keyboard Specifications

## Product status

**Development — native Android input-method foundation.**

GoreeCloud Keyboard is an original GoreeCloud-owned keyboard implementation. The current implemented platform is Android through `InputMethodService` and a first-party rendering/input surface. Apple-platform support remains product direction and is not claimed as currently implemented.

## Current implemented scope

- Native Android `InputMethodService` integration.
- First-party `KeyboardView` rendering and pointer-input handling.
- QWERTY letters with shift, backspace, space, and enter.
- First-party letters/symbols mode switching with `?123`, `ABC`, and `=\\<` controls.
- Primary symbol page with digits and common punctuation plus a secondary first-party page with brackets, operators, currency marks, and common typographic symbols.
- Local-only GoreeCloud Quill suggestion boundary with deterministic prefix suggestions and bounded typo-correction candidates.
- Sensitive-editor classification that clears composing context and suppresses suggestion collection, display, and acceptance for protected input variations.
- No Android network permission in the current application foundation.
- GLAZE UI V1.1 (`1.1.0`) Development source mapping pinned to exact Stable release authority `15cc76d2bcd4065552dc31c77145b63f34d9e7b2`, preserving inherited 4/8 dp spacing, the 12 dp structural small/control radius, 48/56 dp interaction floors and Light/Dark foundation values while adding separate V1.1 optical geometry and an explicit Deep Dark source palette.
- A non-semantic V1.1 Deep Teal + Soft Amber atmosphere source contract exists but is not rendered by `KeyboardView`; Environmental Color Memory, editor/content sampling, remote color derivation, persistent sample history, semantic inference, and animated atmosphere are not enabled.
- Experimental Glaze Motion evaluation remains historical/test-only and is not a production dependency or V1.1 acceptance source.
- Android unit/build and emulator validation infrastructure, including native rendered secondary-symbol navigation and character hit testing.

## Native input behavior

### Letters layer

The default layer exposes QWERTY alphabetic rows. Shift affects alphabetic output only and automatically returns to the unshifted state after a shifted character is committed.

### Primary symbols layer

Tap `?123` from letters to open the primary symbol page. It exposes digits and common punctuation. `ABC` returns directly to letters, while `=\\<` opens the secondary symbol page.

### Secondary symbols layer

The secondary page exposes additional brackets, mathematical/operator punctuation, path/separator characters, currency marks, and common typographic symbols. `ABC` returns directly to letters and `?123` returns directly to the primary symbol page.

Entering any non-letter layer clears the current suggestion composition buffer. Non-letter symbol input does not become Quill suggestion context.

### Editor transitions

Starting or finishing an input view resets the keyboard to the letters layer, clears temporary composing context, resets shift, and re-evaluates the editor privacy classification.

## Privacy and security boundaries

### Privacy Shield / Privacy Center

Keyboard input is minimized by default. The current suggestion path is local-only, and sensitive-editor handling fails closed by suppressing suggestion capture and presentation. The portable preference format is deliberately constrained to the last explicitly selected emoji category; typed text, composing/surrounding editor context, suggestions or learned input, emoji recents/frequency history, emoji search queries, clipboard data, key history, sensitive-editor contents, telemetry, Identity data, and cryptographic secrets are excluded. The V1.1 source mapping adds no editor/content sampling or atmosphere-derived observation. No production claim is made for future cloud-assisted input until an explicit Privacy Shield contract and acceptance evidence exist.

### Wardveil Security / Security Center

The application does not treat UI state as security authority. Future downloadable dictionaries, models, themes, clipboard integrations, voice/input adapters, or other external content require explicit Wardveil validation appropriate to the object and execution boundary before production use.

### Everkeep / Continuity Center

The current one-field portable preference codec plus bounded category-only local export/apply seams are Development portability primitives only. They are not complete Keyboard backup, recovery, synchronization, or Everkeep acceptance. Emoji recents remain usage-derived local history and are not silently included in portability or synchronization scope.

### GoreeCloud Identity / Identity Center

The current native keyboard foundation does not require a GoreeCloud account to type. Future account-backed personalization or synchronization must use GoreeCloud Identity for authentication/authorization and must not make credential material available to the input surface.

### GoreeCloud Mesh / Mesh Center

Future cross-application capabilities must use governed GoreeCloud integration contracts rather than hidden application coupling. Current local typing does not depend on Mesh availability.

### GLAZE UI / Design Center

GLAZE UI V1.1 (`1.1.0`) is the current Stable GoreeCloud design-system target. This repository maps the applicable V1.1 foundation to the native Android keyboard at exact Stable release commit `15cc76d2bcd4065552dc31c77145b63f34d9e7b2`; earlier V1.0 and pre-reset Glaze UI 2.x acceptance are not inherited as V1.1 consumer evidence.

The current source preserves Light/Dark foundation colors, the inherited 12 dp structural small/control radius through the source-compatible `RadiusMediumDp` name, and 48/56 dp normal/Touch-Assistance interaction floors. V1.1 optical geometry references 8/16/24/32 dp plus capsule are represented separately from structural geometry. Deep Dark is explicitly defined from the V1.1 appearance contract, but `KeyboardView` still selects only Light/Dark from Android night mode and does not infer or auto-select Deep Dark. A runtime Deep Dark policy remains separate application work.

The V1.1 atmospheric source contract is explicitly non-semantic and currently unrendered. Deep Teal/Soft Amber cannot communicate sensitive-editor state, privacy, security, protection, focus, selection, identity, recovery, synchronization, or availability. Accessibility and authoritative semantics resolve before atmosphere. Complete rendered/native/accessibility/V1.1 component-state/material-role/tablet-foldable/representative-device/Human Visual Excellence acceptance remains pending. Reduced Motion/Transparency, Increased Contrast, forced-colors/native equivalents, 200% text/reflow, runtime Touch Assistance resolution, RTL/localization, TalkBack/Switch Access, production packaging, release, and Stable qualification remain separate gates.

Local emoji search remains an application-local input-navigation capability and is not GoreeCloud Universal Search. GLAZE UI presentation semantics do not grant Universal Search, Control Center, security, privacy, identity, recovery, or other platform authority to Keyboard.

Historical Glaze Motion 0.5 evaluation remains test-only and provides no current V1.1 production/conformance evidence.

## Target capability families — not yet complete

The product direction includes, subject to separate implementation and acceptance:

- gesture/swipe typing;
- richer local correction and prediction;
- user and language dictionaries;
- multilingual layouts and language switching;
- emoji and further specialized input surfaces beyond the current two symbol pages;
- clipboard tools with explicit privacy controls;
- optional voice/input adapters where platform policy and privacy contracts permit;
- one-handed, split, tablet, foldable, and other adaptive layouts;
- accessibility improvements including TalkBack, switch access, large text, contrast, reduced-transparency, and representative device acceptance;
- GoreeCloud Quill writing assistance through privacy-preserving boundaries;
- user-controlled personalization, portability, backup, and synchronization where explicitly implemented.

These targets are product scope, not current implementation claims.

## Production and Stable acceptance gates

Development source or passing CI is not equivalent to production acceptance or Stable qualification. Production promotion requires evidence appropriate to the shipped platform, including native runtime behavior, privacy/security boundaries, GLAZE UI V1.1 conformance, accessibility, representative devices/form factors, performance and power behavior, signed packaging, release/distribution controls, rollback/recovery, and any applicable GoreeCloud platform-system acceptance.
