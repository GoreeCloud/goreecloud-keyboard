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
MOTION_REFERENCE_REVISION = "b386c793c047e2f5d5d92125732f142e7fdf32dc"
V1_VERSION = "1.0.0"
V1_SOURCE_REVISION = "70909bbdccad378fb7281ae1842e2f5beed64c38"
MARKER = "GlazeMotionExperimental"


def fail(message: str) -> None:
    raise SystemExit(f"Keyboard GLAZE UI V1.0 / Glaze Motion boundary failed: {message}")


def main() -> None:
    for path in (DOC, ADOPTION, PLATFORM, TEST, KEYBOARD_VIEW, TOKENS):
        if not path.is_file():
            fail(f"missing required evidence: {path.relative_to(ROOT)}")

    doc_text = DOC.read_text(encoding="utf-8")
    adoption_text = ADOPTION.read_text(encoding="utf-8")
    platform_text = PLATFORM.read_text(encoding="utf-8")
    test_text = TEST.read_text(encoding="utf-8")
    view_text = KEYBOARD_VIEW.read_text(encoding="utf-8")
    token_text = TOKENS.read_text(encoding="utf-8")

    required_doc = [
        "Lifecycle: **Experimental 0.5**",
        f"Reviewed canonical revision: `{MOTION_REFERENCE_REVISION}`",
        "Runtime compatibility baseline: **0.4.0**",
        "Evaluation mode: **native Android interaction mapping, test-only**",
        "Production dependency: **no**",
        "GLAZE UI V1.0 (`1.0.0`) is the official and only current",
        "provides no V1 production or conformance evidence",
        "insufficient for promotion by itself",
    ]
    for evidence in required_doc:
        if evidence not in doc_text:
            fail(f"missing lifecycle or Motion boundary `{evidence}`")

    required_adoption = [
        "# GLAZE UI V1.0 Migration — GoreeCloud Keyboard",
        "Status: **Migration in progress / Development**",
        "Official target: **GLAZE UI V1.0 (`1.0.0`)**",
        f"Exact V1 source authority: `{V1_SOURCE_REVISION}`",
        "Production eligible on the Glaze UI gate: **no**",
        "does **not** establish complete V1 conformance",
        "No pre-reset Glaze acceptance is inherited as V1 evidence",
        "48 dp",
        "56 dp Touch Assistance / far-view",
        "V1 12 dp small/control radius tier",
        "Dark maps V1 canvas `#0B0D11`",
        "V1 publishes Deep Dark",
        "**Application** surface under the V1 System Shell contract",
        "Local emoji search remains application-local",
        "Historical evidence boundary",
        "Glaze Motion 0.5 evaluation remains test-only",
        "not use a retired Glaze product version as the current rollback target",
    ]
    for evidence in required_adoption:
        if evidence not in adoption_text:
            fail(f"missing V1 adoption boundary `{evidence}`")

    required_tokens = [
        "official GLAZE UI V1.0 foundation subset",
        f'const val TargetVersion = "{V1_VERSION}"',
        f'const val SourceRevision = "{V1_SOURCE_REVISION}"',
        "enum class Appearance { LIGHT, DARK }",
        "const val Space1Dp = 4f",
        "const val Space2Dp = 8f",
        "const val RadiusMediumDp = 12f",
        "const val GeneralInteractionFloorDp = 48f",
        "const val TouchAssistanceInteractionFloorDp = 56f",
        "const val SuggestionStripHeightDp = GeneralInteractionFloorDp",
        "fun interactionFloorDp(touchAssistance: Boolean): Float =",
        "0xFFF5F7FA",
        "0xC7FFFFFF",
        "0xFF151A23",
        "0xFF5D6675",
        "0xFF0B0D11",
        "0xC2181D26",
        "0xFFF5F7FA",
        "0xFFB0B7C3",
    ]
    for evidence in required_tokens:
        if evidence not in token_text:
            fail(f"missing GLAZE UI V1.0 source mapping `{evidence}`")

    if 'required_version: "1.0.0"' not in platform_text:
        fail("Platform Contract must require GLAZE UI V1.0")
    if 'implemented_version: "1.0.0"' not in platform_text:
        fail("Platform Contract must record repository-local V1 mapping")
    if "stable_eligible: false" not in platform_text:
        fail("Platform Contract must preserve Stable eligibility block")

    active_records = {
        "adoption record": adoption_text,
        "Motion boundary": doc_text,
        "native tokens": token_text,
        "Platform Contract": platform_text,
    }
    retired_active_markers = (
        "Glaze UI 2.2.0 Stable is the production design-system authority.",
        "Required Stable baseline: **Glaze UI 2.2.0**",
        "Glaze UI 2.2 Adoption Candidate",
        'required_version: "2.2.0"',
        'implemented_version: "2.2.0"',
    )
    for name, content in active_records.items():
        for retired in retired_active_markers:
            if retired in content:
                fail(f"{name} retains retired active target `{retired}`")

    required_test = [
        f'const val REFERENCE_REVISION = "{MOTION_REFERENCE_REVISION}"',
        'const val VERSION = "0.5.0"',
        'const val RUNTIME_BASELINE = "0.4.0"',
        "Settings.Global.ANIMATOR_DURATION_SCALE",
        "allowsOptionalSettling(",
        "KeyboardView(context)",
        "dispatchTouchEvent(event)",
    ]
    for evidence in required_test:
        if evidence not in test_text:
            fail(f"missing native test-only Motion evidence `{evidence}`")

    required_consumer = [
        "class KeyboardView",
        "Configuration.UI_MODE_NIGHT_MASK",
        "GlazeKeyboardTokens.Appearance.DARK",
        "GlazeKeyboardTokens.Appearance.LIGHT",
        "GlazeKeyboardTokens.palette(appearance)",
        "GlazeKeyboardTokens.SuggestionStripHeightDp",
        "GlazeKeyboardTokens.RadiusMediumDp",
        "override fun onTouchEvent(event: MotionEvent)",
        "listener?.onSuggestion(hit.value)",
        "Action.TEXT -> {",
        "emojiRecents.record(hit.key.label)",
        "listener?.onText(hit.key.label)",
        "performClick()",
    ]
    for evidence in required_consumer:
        if evidence not in view_text:
            fail(f"representative Keyboard interaction surface no longer exposes `{evidence}`")

    production_hits = []
    for path in MAIN.rglob("*.kt"):
        if MARKER in path.read_text(encoding="utf-8"):
            production_hits.append(str(path.relative_to(ROOT)))
    if production_hits:
        fail(
            "Experimental mapping escaped test quarantine into production source: "
            + ", ".join(production_hits)
        )

    print(
        "Keyboard GLAZE UI V1.0 source mapping + Glaze Motion 0.5 test-only boundary passed: "
        f"target {V1_VERSION}, source {V1_SOURCE_REVISION}; Experimental Motion remains quarantined; "
        "runtime/accessibility/device/release acceptance remains separate."
    )


if __name__ == "__main__":
    main()
