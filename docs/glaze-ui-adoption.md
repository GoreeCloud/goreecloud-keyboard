# GLAZE UI V1.2 Stable Adoption — GoreeCloud Keyboard

Status: **Migration and application acceptance in progress / Development**  
Current design-system target: **GLAZE UI V1.2 (`1.2.0`) Stable**  
Canonical repository: `GoreeCloud/goreecloud-glaze-ui`  
Exact Stable promotion revision: `f285b9145e27e6e7027b075c37299d101945c272`  
Production eligible on the Glaze UI gate: **no**

## Scope

GoreeCloud Keyboard now targets the current GLAZE UI V1.2 Stable consumer line. The repository-local native source maps the V1.2 Frosted Neutral material model, appearance roles, geometry, target-size semantics, interaction states, and bounded Android accessibility behavior used by the first-party IME surface.

This does **not** establish complete V1.2 consumer conformance, rendered/native accessibility acceptance, representative-device acceptance, production signing, Release Candidate entry, or Stable qualification. Those remain separate product gates.

The native surface remains first-party `KeyboardView`; no web runtime, remote UI layer, network permission, analytics, advertising, or Experimental Motion production dependency is introduced.

## Implemented V1.2 Stable source mapping

- `GlazeKeyboardTokens.TargetVersion` is `1.2.0` and `SourceRevision` is `f285b9145e27e6e7027b075c37299d101945c272`.
- Governing material rule: **Neutral glass is the material. Color is an accent.**
- Inherited spacing consumed by the keyboard remains 4 dp and 8 dp.
- `RadiusMediumDp` remains a source-compatible alias for the 12 dp control radius role.
- Ordinary interaction floor remains 48 dp; the bounded map retains the 56 dp Touch Assistance / far-view floor without claiming platform preference resolution.
- Optical geometry references 8/16/24/32 dp plus capsule remain separate from structural radius and hit-target authority.
- V1.2 pressed, selected, focus, and increased-contrast focus calibration is represented explicitly in repository-local tokens.
- Ordinary rendered keys consume the V1.2 pressed overlay during active pointer press and clear it on exit, alternate takeover, release, or cancellation without activating Experimental Motion.
- Light keys consume neutral Frosted material `rgba(255,255,255,0.58)`.
- Dark keys consume neutral Frosted material `rgba(25,25,27,0.62)`.
- Deep Dark source material is explicitly defined as `rgba(14,14,16,0.72)` with the V1.2 structural border family.
- Android night mode continues to select Light/Dark only; Deep Dark is not silently inferred from normal dark mode.
- `GlazeKeyboardAtmosphere` makes the neutral-substrate boundary explicit: chromatic tint cannot define the key material substrate.
- Environmental Color Memory, editor/content sampling, remote color derivation, persistent sample history, semantic inference, telemetry, network lookup, and animated atmosphere remain disabled/not authorized.
- Keyboard remains an **Application** surface. Long-press alternates and local emoji search remain local input interactions, not Control Center or Universal Search.
- Existing Quill suggestions, sensitive-editor gating, no-personalized-learning handling, typo correction, emoji, alternate-character, deletion, and key-release semantics remain first-party and on-device.
- The custom-drawn native keyboard exposes rendered keys, visible Quill suggestions, emoji category controls, visible local emoji-search results, and eligible long-press character alternates through bounded Android virtual accessibility nodes/actions.

## Accessibility and interaction polish

Current Development source includes explicit accessibility semantics for custom-drawn controls and eligible long-press alternates. Accessible alternate actions route through the same listener/text-commit authority used by rendered key input, and emoji-search query mode suppresses alternate actions so accessibility cannot bypass the rendered interaction boundary.

These changes improve functionality and discoverability, but repository source/runtime tests are not substitutes for representative TalkBack, Switch Access, Voice Access, OEM/compositor, large-text, localization/RTL, Touch Assistance, or physical-device ergonomics acceptance.

## Governance and presentation boundary

Keyboard follows GLAZE UI V1.2 while retaining the principle that semantic clarity outranks optical effects. Key labels, suggestions, focus/selection, and sensitive-input behavior remain higher priority than translucency or accent treatment.

Removing blur, translucency, or other advanced effects must never remove content, actions, focus, semantic state, hierarchy, or target size. Accent color cannot mean privacy, security, protection, identity, recovery, synchronization, availability, sensitive-editor state, focus, or selection unless an authoritative semantic contract grants that meaning.

GLAZE UI grants no clipboard, editor-observation, learning, network, Identity, Mesh, Everkeep, Privacy Shield, Wardveil Security, or Manager authority.

## Privacy boundary

The V1.2 mapping adds no new observation path. In particular it adds no typed/composing/surrounding-text read, learned-input persistence, clipboard access, editor/content color sampling, telemetry, remote design/color derivation, network permission, Identity/Mesh session, or background synchronization.

The one-field `goreecloud-keyboard-preferences/1` portability boundary remains privacy-minimized and currently contains only the explicitly selected emoji category.

## Repository-local evidence

- `android/app/src/main/kotlin/com/goreecloud/keyboard/KeyboardView.kt` — native rendering and pointer-input surface.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/KeyboardAccessibilityDelegate.kt` — bounded virtual accessibility-node bridge, including eligible long-press alternates.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/GlazeKeyboardTokens.kt` — exact V1.2 Stable structural/material/state mapping.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/GlazeKeyboardAtmosphere.kt` — neutral-substrate and non-semantic atmosphere boundary.
- `android/app/src/test/kotlin/com/goreecloud/keyboard/GlazeKeyboardTokensTest.kt` — exact source provenance and material/target/state assertions.
- Android instrumentation tests — native interaction/accessibility runtime evidence.
- Android manifest — no network permission.
- Android CI — Glaze/Motion governance, privacy/accessibility source guards, JVM tests, debug assembly, and native emulator interaction validation.

## Platform-system boundary

All seven GoreeCloud platform-system evaluations remain independently governed in `goreecloud.platform.yaml`: GoreeCloud Manager, Privacy Shield, Wardveil Security, Everkeep, GLAZE UI, GoreeCloud Mesh, and GoreeCloud Identity. A correct V1.2 UI mapping cannot manufacture acceptance for the other six systems.

## Acceptance still required

- complete V1.2 component/state/material-role mapping across every keyboard/settings surface;
- Reduced Transparency / solid fallback acceptance;
- Increased Contrast and forced-colors/native-equivalent acceptance;
- Reduced Motion acceptance for applicable production behavior;
- 200% large-text/reflow within host IME constraints;
- platform Touch Assistance detection and 56 dp assisted geometry;
- RTL/localization expansion;
- representative TalkBack/Switch Access/Voice Access acceptance;
- representative phone/tablet/foldable and host-IME adaptation;
- representative physical-device long-press/slide/release ergonomics;
- representative physical-device performance, power, thermal, and latency acceptance;
- Human Visual Excellence review of the actual Keyboard consumer;
- Privacy Shield and Wardveil Security acceptance appropriate to sensitive input processing;
- Everkeep acceptance for any approved durable-state recovery scope;
- Mesh/Identity/Manager integration where applicable and authorized;
- production signing, distribution, release approval, and Release Candidate qualification.

Source/build/emulator success remains Development evidence only until those applicable runtime and release gates are satisfied.

## Glaze Motion boundary

Historical Glaze Motion 0.5 evaluation remains test-only. Glaze Motion is separately governed Experimental work and is not promoted by the V1.2 Stable design-system target. The pressed-state feedback in production `KeyboardView` is an immediate deterministic state overlay and does not activate the Experimental Motion subsystem.

## Rollback

If the V1.2 mapping causes a regression, revert the exact Keyboard migration revision to the prior validated application revision. Do not weaken the canonical GLAZE UI V1.2 contract or relabel a superseded design-system line as current authority merely to preserve a consumer implementation.
