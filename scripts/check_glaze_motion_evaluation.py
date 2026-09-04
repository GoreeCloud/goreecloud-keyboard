#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DOC = ROOT / "docs/glaze-motion-evaluation.md"
ADOPTION = ROOT / "docs/glaze-ui-adoption.md"
PLATFORM = ROOT / "goreecloud.platform.yaml"
TEST = ROOT / "android/app/src/androidTest/kotlin/com/goreecloud/keyboard/GlazeMotionExperimentalKeyboardRuntimeTest.kt"
MAIN = ROOT / "android/app/src/main"
KEYBOARD_VIEW = MAIN / "kotlin/com/goreecloud/keyboard/KeyboardView.kt"
TOKENS = MAIN / "kotlin/com/goreecloud/keyboard/GlazeKeyboardTokens.kt"
ATMOSPHERE = MAIN / "kotlin/com/goreecloud/keyboard/GlazeKeyboardAtmosphere.kt"
MOTION_REFERENCE_REVISION = "b386c793c047e2f5d5d92125732f142e7fdf32dc"
GLAZE_VERSION = "1.1.0"
GLAZE_SOURCE_REVISION = "15cc76d2bcd4065552dc31c77145b63f34d9e7b2"
MARKER = "GlazeMotionExperimental"


def fail(message: str) -> None:
    raise SystemExit(f"Keyboard GLAZE UI V1.1 / Platform Contract v0.2 boundary failed: {message}")


def require_all(label: str, text: str, markers: tuple[str, ...]) -> None:
    for marker in markers:
        if marker not in text:
            fail(f"{label} missing `{marker}`")


def main() -> None:
    for path in (DOC, ADOPTION, PLATFORM, TEST, KEYBOARD_VIEW, TOKENS, ATMOSPHERE):
        if not path.is_file():
            fail(f"missing required evidence: {path.relative_to(ROOT)}")

    doc_text = DOC.read_text(encoding="utf-8")
    adoption_text = ADOPTION.read_text(encoding="utf-8")
    platform_text = PLATFORM.read_text(encoding="utf-8")
    test_text = TEST.read_text(encoding="utf-8")
    view_text = KEYBOARD_VIEW.read_text(encoding="utf-8")
    token_text = TOKENS.read_text(encoding="utf-8")
    atmosphere_text = ATMOSPHERE.read_text(encoding="utf-8")

    require_all(
        "Motion boundary",
        doc_text,
        (
            "Lifecycle: **Experimental 0.5**",
            f"Reviewed canonical revision: `{MOTION_REFERENCE_REVISION}`",
            "Runtime compatibility baseline: **0.4.0**",
            "Evaluation mode: **native Android interaction mapping, test-only**",
            "Production dependency: **no**",
            "GLAZE UI V1.1 (`1.1.0`) is the current Stable",
            "Motion remains separately Experimental",
            "insufficient for promotion by itself",
        ),
    )

    require_all(
        "V1.1 adoption record",
        adoption_text,
        (
            "# GLAZE UI V1.1 Migration — GoreeCloud Keyboard",
            "Status: **Migration in progress / Development**",
            "Official target: **GLAZE UI V1.1 (`1.1.0`)**",
            f"Exact Stable source authority: `{GLAZE_SOURCE_REVISION}`",
            "Production eligible on the Glaze UI gate: **no**",
            "does **not** establish complete V1.1 consumer conformance",
            "V1.1 optical geometry references 8/16/24/32 dp plus capsule",
            "Deep Dark is now explicitly defined",
            "KeyboardView` continues to select only Light/Dark from Android night mode",
            "Atmosphere is **not rendered by KeyboardView**",
            "editor/content sampling",
            "one-field `goreecloud-keyboard-preferences/1` portability boundary remains unchanged",
            "Glaze Motion 0.5 evaluation remains test-only",
        ),
    )

    require_all(
        "V1.1 token mapping",
        token_text,
        (
            f'const val TargetVersion = "{GLAZE_VERSION}"',
            f'const val SourceRevision = "{GLAZE_SOURCE_REVISION}"',
            "enum class Appearance { LIGHT, DARK, DEEP_DARK }",
            "const val Space1Dp = 4f",
            "const val Space2Dp = 8f",
            "const val RadiusMediumDp = 12f",
            "const val GeneralInteractionFloorDp = 48f",
            "const val TouchAssistanceInteractionFloorDp = 56f",
            "const val OpticalMicroDp = 8f",
            "const val OpticalControlDp = 16f",
            "const val OpticalContainerDp = 24f",
            "const val OpticalHeroDp = 32f",
            "const val OpticalCapsuleDp = 999f",
            "0xFFF5F7FA",
            "0xFF0B0D11",
            "0xFF05070A",
            "0xCC12161D",
            "0xFFABB4C2",
            "Appearance.DEEP_DARK -> DeepDarkPalette",
        ),
    )

    require_all(
        "V1.1 atmosphere contract",
        atmosphere_text,
        (
            "Non-semantic GLAZE UI V1.1 atmospheric source contract",
            "const val DeepTealArgb = 0xFF0F6B6F.toInt()",
            "const val SoftAmberArgb = 0xFFD9A35F.toInt()",
            "const val EnvironmentalColorMemoryEnabled = false",
            "const val RemoteColorDerivationAllowed = false",
            "const val PersistentSampleHistoryAllowed = false",
            "const val SemanticInferenceAllowed = false",
            "no editor/content sampling",
        ),
    )

    require_all(
        "Platform Contract v0.2",
        platform_text,
        (
            'schema_version: "0.2"',
            "  id: goreecloud-keyboard",
            '  glaze_ui:\n    result: applicable-migration-required\n    version: "1.1.0"',
            "android/app/src/main/kotlin/com/goreecloud/keyboard/GlazeKeyboardAtmosphere.kt",
            '  platform_contract: "0.2"',
            '  glaze_ui_required: "1.1.0"',
            "goreecloud-platform-contract==0.2",
            "glaze-ui==1.1.0",
            "conformance:\n  status: nonconformant",
            "runtime Deep Dark policy",
        ),
    )

    require_all(
        "representative Keyboard runtime",
        view_text,
        (
            "class KeyboardView",
            "Configuration.UI_MODE_NIGHT_MASK",
            "GlazeKeyboardTokens.Appearance.DARK",
            "GlazeKeyboardTokens.Appearance.LIGHT",
            "GlazeKeyboardTokens.palette(appearance)",
            "GlazeKeyboardTokens.SuggestionStripHeightDp",
            "GlazeKeyboardTokens.RadiusMediumDp",
            "override fun onTouchEvent(event: MotionEvent)",
            "listener?.onSuggestion(hit.value)",
            "emojiRecents.record(hit.key.label)",
            "listener?.onText(hit.key.label)",
            "performClick()",
        ),
    )

    if "Appearance.DEEP_DARK" in view_text:
        fail("KeyboardView must not infer or auto-select Deep Dark in this source-mapping slice")
    if "GlazeKeyboardAtmosphere" in view_text:
        fail("KeyboardView must not render V1.1 atmosphere in this source-mapping slice")

    require_all(
        "native test-only Motion evidence",
        test_text,
        (
            f'const val REFERENCE_REVISION = "{MOTION_REFERENCE_REVISION}"',
            'const val VERSION = "0.5.0"',
            'const val RUNTIME_BASELINE = "0.4.0"',
            "Settings.Global.ANIMATOR_DURATION_SCALE",
            "allowsOptionalSettling(",
            "KeyboardView(context)",
            "dispatchTouchEvent(event)",
        ),
    )

    production_hits = []
    for path in MAIN.rglob("*.kt"):
        if MARKER in path.read_text(encoding="utf-8"):
            production_hits.append(str(path.relative_to(ROOT)))
    if production_hits:
        fail(
            "Experimental Motion escaped test quarantine into production source: "
            + ", ".join(production_hits)
        )

    active_records = adoption_text + "\n" + doc_text + "\n" + token_text + "\n" + platform_text
    for stale in (
        "Official target: **GLAZE UI V1.0 (`1.0.0`)**",
        "GLAZE UI V1.0 (`1.0.0`) is the official and only current",
        '  glaze_ui:\n    result: applicable-migration-required\n    version: "1.0.0"',
        "Glaze UI 2.2.0 Stable is the production design-system authority.",
        "Required Stable baseline: **Glaze UI 2.2.0**",
        "stable_eligible: true",
    ):
        if stale in active_records:
            fail(f"active evidence retains stale design-system boundary `{stale}`")

    print(
        "Keyboard GLAZE UI V1.1 source mapping + Glaze Motion 0.5 test-only boundary passed: "
        f"source target {GLAZE_VERSION}, source {GLAZE_SOURCE_REVISION}; Platform Contract v0.2 remains "
        "migration-required/nonconformant; Keyboard runtime remains Android Light/Dark only; atmosphere is source-only; "
        "Experimental Motion remains quarantined; rendered/accessibility/device/release acceptance remains separate."
    )


if __name__ == "__main__":
    main()
