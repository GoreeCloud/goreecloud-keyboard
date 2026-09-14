# GLAZE UI V1.4 Development Mapping — GoreeCloud Keyboard

Status: **Migration in progress / Development**  
Current Stable target: **GLAZE UI V1.4 (`1.4.0`)**  
Canonical repository: `GoreeCloud/goreecloud-glaze-ui`  
Exact Stable merged source authority: `84cb3db4884042f0fa25ed6d475a127fb110f596`  
Production eligible on the Glaze UI gate: **no**

## Scope

GoreeCloud Keyboard now targets the current governed Stable GLAZE UI V1.4 / `1.4.0` design-system authority through a first-party native Android mapping. The migration preserves the existing neutral Frosted material, geometry, target-size, appearance, interaction-state, accessibility, and truth boundaries while adding a Keyboard-specific V1.4 Optical Intelligence resolver.

This mapping remains **Development evidence only**. It does not establish complete V1.4 consumer conformance, rendered/accessibility/device acceptance, production approval, signed release, Release Candidate entry, or Stable Keyboard qualification.

The native surface remains first-party `KeyboardView`; no web runtime, remote UI layer, network permission, analytics, advertising, or Experimental Motion production dependency is introduced.

## Implemented V1.4 mapping

- `GlazeKeyboardTokens.TargetVersion` is `1.4.0` and `SourceRevision` records exact merged Stable Glaze source revision `84cb3db4884042f0fa25ed6d475a127fb110f596`.
- Governing material rule remains: **Neutral glass is the material. Color is an accent.**
- Inherited spacing consumed by the keyboard remains 4 dp and 8 dp.
- `RadiusMediumDp` remains the 12 dp control-role mapping.
- The suggestion strip and ordinary interaction floor remain 48 dp; the bounded map retains the 56 dp Touch Assistance / far-view floor without claiming platform preference resolution.
- Optical geometry references 8/16/24/32 dp plus capsule remain separate from structural radius and hit-target authority.
- Existing pressed, selected, focus, and increased-contrast focus calibration remains explicit and test locked.
- Light, Dark, and Deep Dark source palettes remain neutral Frosted material mappings. Runtime appearance selection remains Android Light/Dark only until a separately reviewed Deep Dark runtime policy exists.
- `GlazeKeyboardOptics` adds a deterministic V1.4 Optical Intelligence resolver with maximum semantic protection for this highly sensitive input surface.
- Reduced Transparency and Forced Colors fail closed to `SOLID_ACCESSIBLE`, zero blur, zero decorative tint, and full semantic protection.
- Increased Contrast raises frost protection without enabling decorative color behavior.
- Environmental Color Memory influence is intentionally **0%** for Keyboard even though generic Glaze V1.4 permits bounded memory on less-sensitive surfaces.
- Decorative environment tinting/aura is disabled for Keyboard.
- Editor text, composing text, surrounding text, suggestion content, clipboard state, application identity, emoji history, and key history are forbidden optical-context inputs.
- Remote context and telemetry are not required or authorized by the Keyboard optical resolver.
- Keyboard remains an **Application** surface. Local emoji search, suggestions, and alternate-character interaction remain input features, not Control Center or Universal Search.

## Optical privacy boundary

Keyboard adopts a deliberately narrower V1.4 optical policy than ordinary application surfaces.

The following data must never be used to select frost, tint, blur, warmth, depth, material color, or any other visual treatment:

- typed text;
- composing text;
- surrounding editor text;
- suggestions or correction candidates;
- clipboard contents;
- application/package identity;
- sensitive-editor classification details beyond the existing functional privacy gate;
- emoji search queries or recents;
- key history or typing cadence;
- learned language/profile data; or
- network/remote context.

V1.4 visual adaptation therefore cannot create a new observation channel over the IME. `GlazeKeyboardOptics` consumes only explicitly supplied non-content presentation/accessibility state and itself performs no collection.

## Accessibility precedence

For Keyboard, accessibility and semantic legibility outrank all optical expression:

1. Forced Colors / native equivalent.
2. Reduced Transparency.
3. Increased Contrast and focus visibility.
4. Key/suggestion/control semantic clarity and target size.
5. Neutral material expression.

No optical mode may remove a key label, suggestion, focus state, selected state, action, hierarchy, or interaction target.

## Governance and presentation boundary

`goreecloud.platform.yaml` keeps `platform_systems.glaze_ui.result` as `applicable-migration-required` and product `conformance.status` as `nonconformant` until complete Keyboard-specific acceptance exists.

Keyboard preserves the Glaze presentation principle: **Solid where users read or make explicit critical decisions. Glazed where users interact with transient navigation, command, search, control, or feedback chrome.** On an IME, legibility, input correctness, privacy, accessibility, and latency are always more important than optical richness.

Accent or material color cannot imply privacy, security, protection, identity, recovery, synchronization, availability, sensitive-editor state, focus, or selection unless the applicable authoritative semantic contract grants that meaning.

## Existing privacy boundary remains unchanged

The V1.4 migration adds no new observation path and no new Android permission. In particular it adds no:

- typed/composing/surrounding-text read for visual purposes;
- suggestion or learned-input persistence;
- clipboard observation;
- editor/content sampling for color;
- environmental sample persistence;
- telemetry or analytics;
- remote design/color derivation;
- network permission;
- Identity or Mesh session; or
- background synchronization.

The existing one-field `goreecloud-keyboard-preferences/1` portability boundary remains unchanged and contains only the explicitly selected emoji category.

## Repository-local evidence

- `android/app/src/main/kotlin/com/goreecloud/keyboard/KeyboardView.kt` — first-party native rendering/input surface using the neutral token mapping.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/GlazeKeyboardTokens.kt` — current V1.4 source provenance, geometry, appearance, interaction, and neutral-material mapping.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/GlazeKeyboardOptics.kt` — V1.4 privacy-safe Optical Intelligence policy.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/GlazeKeyboardAtmosphere.kt` — non-semantic neutral-substrate and no-sensitive-observation boundary.
- `android/app/src/test/kotlin/com/goreecloud/keyboard/GlazeKeyboardTokensTest.kt` — exact source/provenance/material/privacy contract coverage.
- `android/app/src/test/kotlin/com/goreecloud/keyboard/GlazeKeyboardOpticsTest.kt` — optical accessibility and sensitive-source prohibition coverage.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/KeyboardAccessibilityDelegate.kt` — bounded Android virtual-view accessibility bridge.
- `scripts/check_glaze_motion_evaluation.py` — fail-closed Glaze/Motion/source-governance guard.
- Android manifest — no network permission.
- Android CI — governance, editor-privacy, accessibility, JVM tests, debug assembly, and emulator validation.

## V1.4.1 human-validation boundary

By the shared Glaze UI V1.4 release decision, human validation and human verification are assigned to V1.4.1 rather than blocking the shared V1.4.0 lifecycle. Keyboard does **not** represent those activities as passed V1.4.0 evidence.

Applicable V1.4.1 work includes manual assistive-technology review, representative physical-device ergonomics, subjective optical-quality review, and real-device latency/performance/power/thermal qualification. Keyboard product release requirements remain independent and may be stricter.

## Acceptance still required

This source mapping still does not establish:

- a reviewed runtime policy for selecting Deep Dark, if Keyboard should expose one;
- complete component/state/material-role mapping across every keyboard/settings surface;
- actual wiring of Reduced Transparency, Increased Contrast, and forced-color/native-equivalent system preferences into every applicable runtime surface;
- selected/focus state runtime coverage for every applicable control;
- Reduced Motion behavior;
- 200% large-text/reflow within host IME constraints;
- platform Touch Assistance detection and 56 dp assisted geometry;
- RTL/localization expansion;
- representative TalkBack/Switch Access acceptance, including focus order, exploration behavior, host-IME interaction, long-press alternate discovery/activation, and physical-device ergonomics;
- representative phone/tablet/foldable and host-IME adaptation;
- representative physical-device long-press/slide/release ergonomics;
- representative physical-device performance, power, thermal, and latency acceptance;
- V1.4.1 human visual/optical validation applicable to Keyboard;
- Privacy Shield and Wardveil Security acceptance appropriate to sensitive input processing;
- Everkeep acceptance for any approved durable-state recovery scope;
- Mesh/Identity integration only where applicable and authorized;
- Manager visibility/administrative integration where required; or
- production signing, distribution, release approval, and Stable qualification.

Source/build/emulator success remains Development evidence only until those applicable runtime and release gates are satisfied.

## Glaze Motion boundary

Historical Glaze Motion 0.5 evaluation remains test-only. Motion is separately governed Experimental work and is not promoted by the V1.4 design-system migration. Immediate pressed-state feedback in production `KeyboardView` does not activate the Experimental Motion subsystem.

## Rollback

V1.3 is the immediate shared Glaze rollback baseline. If the Keyboard V1.4 mapping itself regresses, revert the Keyboard migration commits while preserving the canonical current Glaze lifecycle authority. A local rollback does not authorize relabeling an older Glaze release as current Stable.
