# GLAZE UI V1.2 Migration — GoreeCloud Keyboard

Status: **Migration in progress / Development**  
Official target: **GLAZE UI V1.2 (`1.2.0`)**  
Canonical repository: `GoreeCloud/goreecloud-glaze-ui`  
Exact Stable source authority: `f285b9145e27e6e7027b075c37299d101945c272`  
Production eligible on the Glaze UI gate: **no**

## Scope

GLAZE UI V1.2 / `1.2.0` is the current Stable GoreeCloud design-system target. This repository-local migration maps the applicable V1.2 foundation, Frosted Neutral material, appearance, geometry, target-size, interaction-state, and bounded native accessibility contract into GoreeCloud Keyboard's first-party Android surface. It does **not** establish complete V1.2 consumer conformance, production acceptance, representative-device acceptance, signed release, or Stable qualification. GLAZE UI V1.1 / `1.1.0` remains the prior known-good Stable rollback anchor; V1.0 and pre-reset Glaze evidence remain historical migration evidence only.

The native surface remains first-party `KeyboardView`; no web runtime, remote UI layer, network permission, analytics, advertising, or Experimental Motion production dependency is introduced.

## Implemented V1.2 source mapping

- `GlazeKeyboardTokens.TargetVersion` is `1.2.0` and `SourceRevision` pins Stable release commit `f285b9145e27e6e7027b075c37299d101945c272`.
- Governing material rule: **Neutral glass is the material. Color is an accent.**
- Inherited spacing consumed by the keyboard remains 4 dp and 8 dp.
- Existing `RadiusMediumDp` remains a source-compatible alias for the V1.2 12 dp control radius role.
- The suggestion strip and ordinary interaction floor remain 48 dp; the bounded map retains the 56 dp Touch Assistance / far-view floor without claiming platform preference resolution.
- Optical geometry references 8/16/24/32 dp plus capsule remain separate from structural radius and hit-target authority.
- V1.2 pressed, selected, focus, and increased-contrast focus calibration is represented explicitly in repository-local tokens.
- Ordinary rendered keys consume the V1.2 pressed overlay (`0.095`) during an active pointer press. The overlay follows the currently touched key, clears when the pointer leaves all key bounds, clears when a long-press alternate popup takes over interaction, and clears on release or cancellation without introducing animation or Experimental Motion runtime authority.
- Light keys consume the V1.2 neutral base-glass material `rgba(255,255,255,0.58)` rather than the older V1.1 material mapping.
- Dark keys consume the V1.2 neutral base-glass material `rgba(25,25,27,0.62)`.
- Deep Dark source material is explicitly defined as `rgba(14,14,16,0.72)` with the V1.2 structural border family.
- `KeyboardView` continues to select only Light/Dark from Android night mode at draw time. Ordinary Android dark mode is not silently treated as Deep Dark. No new user appearance preference is added by this slice.
- `GlazeKeyboardAtmosphere` records the V1.2 neutral-substrate boundary: default chromatic material tint contribution is zero; teal, green, aqua, amber, brand, and semantic color cannot define the keyboard substrate.
- Environmental aura remains optional and external to the substrate. Environmental Color Memory, editor/content sampling, remote color derivation, persistent sample history, semantic inference, telemetry, network lookup, and animated atmosphere remain disabled/not authorized.
- Keyboard remains an **Application** surface. Long-press alternates and local emoji search remain local input interactions, not Control Center or Universal Search.
- Existing Quill suggestions, sensitive-editor gating, typo correction, emoji, alternate-character, deletion, and key-release semantics remain first-party and on-device.
- The custom-drawn native keyboard now has a bounded Android virtual-view accessibility foundation. `KeyboardAccessibilityDelegate` uses `ExploreByTouchHelper` to expose rendered keys, visible Quill suggestions, emoji category controls, and visible local emoji-search results as actionable virtual button nodes. Virtual activation delegates to the same first-party semantic handlers used by touch input rather than creating a second text-commit authority.
- Virtual nodes expose meaningful control labels and selected state where applicable, including Shift and the selected emoji category. Hover exploration is delegated through the Android accessibility helper, and structural changes invalidate the virtual root so assistive technology can refresh visible controls.

## V1.2 authority and presentation boundary

Keyboard follows V1.2's material and interaction hierarchy while retaining the inherited principle: **Solid where users read or make explicit critical decisions. Glazed where users interact with transient navigation, command, search, control, or feedback chrome.** For an IME, key labels, suggestion content, selection/focus indication, and sensitive-input behavior are higher priority than optical effects.

The V1.2 authority order remains controlling: producer-authoritative protected meaning and accessibility resolution precede optical presentation. Removing blur, translucency, aura, or other advanced effects must never remove content, actions, focus, semantic state, hierarchy, or target size.

Accent color cannot mean privacy, security, protection, identity, recovery, synchronization, availability, sensitive-editor state, focus, or selection unless the applicable authoritative semantic contract explicitly grants that meaning. Glaze presentation grants no clipboard, editor-observation, learning, network, Identity, Mesh, Everkeep, Privacy Shield, or Wardveil Security authority.

## Privacy boundary

The V1.2 migration adds no new observation path. In particular it adds no:

- typed/composing/surrounding text read;
- suggestion or learned-input persistence;
- emoji-recents or emoji-search export;
- clipboard access;
- editor/content sampling for color;
- telemetry or analytics;
- remote design or color derivation;
- network permission;
- Identity or Mesh session; or
- background synchronization.

Pressed-state rendering consumes only pointer geometry already required by the native key interaction path. It does not inspect editor content, clipboard state, suggestions, language state, application identity, or network data.

The virtual accessibility delegate consumes only the already-rendered interaction geometry and visible control labels supplied by `KeyboardView`. It does not open or inspect an `InputConnection`, read surrounding editor text, access the clipboard, read or write preference state, persist exploration history, perform network access, or create a new semantic commit path. The keyboard service remains authoritative for Shift-aware text commit behavior.

The existing one-field `goreecloud-keyboard-preferences/1` portability boundary remains unchanged and still contains only the explicitly selected emoji category.

## Repository-local evidence

- `android/app/src/main/kotlin/com/goreecloud/keyboard/KeyboardView.kt` — first-party rendering/pointer-input surface; consumes neutral V1.2 key material and native pressed-state feedback while retaining Android Light/Dark runtime selection; publishes current rendered controls to the accessibility delegate and routes virtual activation through shared semantic handlers.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/KeyboardAccessibilityDelegate.kt` — bounded `ExploreByTouchHelper` virtual-node bridge for custom-drawn keys, suggestions, emoji category controls, and visible local emoji-search results.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/GlazeKeyboardTokens.kt` — bounded V1.2 structural/material/state source mapping including explicit Deep Dark source values.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/GlazeKeyboardAtmosphere.kt` — V1.2 neutral-substrate and non-semantic atmosphere boundary.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/AlternatePopupLayout.kt` — viewport-bounded long-press geometry/hit-test authority.
- `GlazeKeyboardTokensTest` — exact V1.2 provenance, inherited geometry, neutral Light/Dark/Deep Dark materials, state calibration, interaction floors, and atmosphere-observation/tinting assertions.
- `GlazeKeyboardV12VisualStateRuntimeTest` — native emulator rendering evidence that an ordinary key changes visually on press, returns to idle presentation on release/cancel, and retains release-only semantic commit behavior.
- `KeyboardAccessibilityRuntimeTest` — Android instrumentation evidence that rendered custom controls become virtual button nodes, virtual activation reaches the ordinary listener path, suggestions remain actionable, emoji search controls remain discoverable, and selected-state presentation is surfaced.
- `scripts/check_keyboard_accessibility.py` — fail-closed repository guard for the virtual-node integration and its minimized data-authority boundary.
- `AlternatePopupLayoutTest` — normal, edge, compact multi-row, failure, gap/unused-cell, outside-point, and non-finite hit-test behavior.
- Android manifest — no network permission.
- Android CI — repository Glaze/Motion governance, editor-privacy and accessibility source guards, JVM tests, debug assembly, and native emulator interaction validation.

## Acceptance still required

This source migration still does not establish:

- a reviewed runtime policy for selecting Deep Dark, if Keyboard should expose one;
- complete V1.2 component/state/material-role mapping across every keyboard/settings surface;
- selected/focus state runtime coverage for every applicable control surface;
- Reduced Transparency / solid fallback acceptance;
- Increased Contrast and forced-colors/native-equivalent acceptance;
- Reduced Motion;
- 200% large-text/reflow within host IME constraints;
- platform Touch Assistance detection and 56 dp assisted geometry;
- RTL/localization expansion;
- representative TalkBack/Switch Access acceptance, including focus order, exploration behavior, host-IME interaction, long-press alternate discovery/activation, and physical-device ergonomics;
- representative phone/tablet/foldable and host-IME adaptation;
- representative physical-device long-press/slide/release ergonomics;
- representative physical-device performance, power, thermal, and latency acceptance;
- Human Visual Excellence review of the actual Keyboard consumer;
- Privacy Shield and Wardveil Security acceptance appropriate to sensitive input processing;
- Everkeep acceptance for any approved durable-state recovery scope;
- Mesh/Identity integration only where applicable and authorized;
- Manager visibility/administrative integration where required; or
- production signing, distribution, release approval, and Stable qualification.

Source/build/emulator success remains Development evidence only until those applicable runtime and release gates are satisfied.

## Glaze Motion boundary

Historical Glaze Motion 0.5 evaluation remains test-only. Glaze Motion is separately governed Experimental work and is not promoted by V1.2. The pressed-state feedback in production `KeyboardView` is an immediate deterministic V1.2 state overlay and does not activate the Experimental Motion subsystem. Glaze Motion is not a production dependency and cannot establish V1.2 consumer acceptance.

## Rollback

If this V1.2 source/material/accessibility migration causes a regression, revert the exact Keyboard V1.2 migration commit/merge to the prior validated Keyboard V1.2 main revision. GLAZE UI V1.1 / `1.1.0` remains the prior known-good Stable design-system rollback anchor. Do not weaken the canonical V1.2 contract or reactivate an older release as the current product target merely to bypass a consumer defect.
